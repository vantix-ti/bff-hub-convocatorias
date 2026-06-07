package cl.vantix.hub.bff.infrastructure.web.filter;

import cl.vantix.hub.bff.infrastructure.config.BffSecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

/**
 * Filtro de seguridad del BFF.
 * Valida que cada request incluya el header X-API-Key correcto.
 * Rutas excluidas: /swagger-ui/**, /v3/api-docs/**, /actuator/**
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ApiKeyFilter extends GenericFilter {

    private final BffSecurityProperties securityProps;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Rutas públicas — sin validación de API Key
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        String apiKey = req.getHeader(securityProps.getHeaderName());

        if (apiKey == null || !apiKey.equals(securityProps.getApiKey())) {
            log.warn("API-Key inválida o ausente. Path: {} | IP: {}", path, req.getRemoteAddr());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(res.getWriter(), Map.of(
                    "status",    401,
                    "mensaje",   "API Key inválida o ausente.",
                    "timestamp", Instant.now().toString()
            ));
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return path.contains("/swagger-ui")
            || path.contains("/v3/api-docs")
            || path.contains("/actuator")
            || path.contains("/instituciones/slug/")
            || path.contains("/configuracion/public/");
    }
}

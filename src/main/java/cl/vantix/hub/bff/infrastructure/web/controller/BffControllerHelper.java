package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.model.HubResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;

/**
 * Utilidad compartida por todos los controllers BFF.
 * Convierte HubResponse en ResponseEntity propagando el status del microservicio.
 */
final class BffControllerHelper {

    private BffControllerHelper() {}

    static ResponseEntity<String> toResponse(HubResponse hub) {
        HttpHeaders headers = new HttpHeaders();
        if (hub.contentType() != null) {
            headers.set(HttpHeaders.CONTENT_TYPE, hub.contentType());
        }
        return ResponseEntity.status(hub.status()).headers(headers).body(hub.body());
    }

    /** Extrae el JWT del header Authorization del request entrante. */
    static String extractJwt(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}

package cl.vantix.hub.bff.infrastructure.client;

import cl.vantix.hub.bff.domain.model.HubResponse;
import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Adaptador HTTP que llama a ms-hub-convocatorias usando Spring RestClient.
 * Propaga el status code y body de forma transparente.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MsHubClientAdapter implements MsHubClientPort {

    private final RestClient msHubRestClient;

    // ── GET ──────────────────────────────────────────────────────────────────

    @Override
    public HubResponse get(String path, String jwtToken) {
        return get(path, jwtToken, null);
    }

    @Override
    public HubResponse get(String path, String jwtToken, Map<String, String> queryParams) {
        try {
            String uri = buildUri(path, queryParams);
            ResponseEntity<String> resp = msHubRestClient.get()
                    .uri(uri)
                    .headers(h -> addAuth(h, jwtToken))
                    .retrieve()
                    .toEntity(String.class);
            return new HubResponse(resp.getStatusCode().value(), resp.getBody(), contentType(resp));
        } catch (HttpStatusCodeException ex) {
            return errorResponse(ex);
        }
    }

    // ── GET bytes (PDF) ───────────────────────────────────────────────────────

    @Override
    public HubResponse getBytes(String path, String jwtToken) {
        try {
            ResponseEntity<byte[]> resp = msHubRestClient.get()
                    .uri(path)
                    .headers(h -> addAuth(h, jwtToken))
                    .retrieve()
                    .toEntity(byte[].class);
            String body = resp.getBody() != null
                    ? java.util.Base64.getEncoder().encodeToString(resp.getBody()) : "";
            return new HubResponse(resp.getStatusCode().value(), body, "application/pdf");
        } catch (HttpStatusCodeException ex) {
            return errorResponse(ex);
        }
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Override
    public HubResponse post(String path, Object body, String jwtToken) {
        try {
            ResponseEntity<String> resp = msHubRestClient.post()
                    .uri(path)
                    .headers(h -> addAuth(h, jwtToken))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .toEntity(String.class);
            return new HubResponse(resp.getStatusCode().value(), resp.getBody(), contentType(resp));
        } catch (HttpStatusCodeException ex) {
            return errorResponse(ex);
        }
    }

    // ── PUT ──────────────────────────────────────────────────────────────────

    @Override
    public HubResponse put(String path, Object body, String jwtToken) {
        try {
            ResponseEntity<String> resp = msHubRestClient.put()
                    .uri(path)
                    .headers(h -> addAuth(h, jwtToken))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .toEntity(String.class);
            return new HubResponse(resp.getStatusCode().value(), resp.getBody(), contentType(resp));
        } catch (HttpStatusCodeException ex) {
            return errorResponse(ex);
        }
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Override
    public HubResponse delete(String path, String jwtToken) {
        try {
            ResponseEntity<String> resp = msHubRestClient.delete()
                    .uri(path)
                    .headers(h -> addAuth(h, jwtToken))
                    .retrieve()
                    .toEntity(String.class);
            return new HubResponse(resp.getStatusCode().value(), resp.getBody(), contentType(resp));
        } catch (HttpStatusCodeException ex) {
            return errorResponse(ex);
        }
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private void addAuth(HttpHeaders headers, String jwtToken) {
        if (jwtToken != null && !jwtToken.isBlank()) {
            headers.setBearerAuth(jwtToken);
        }
    }

    private String buildUri(String path, Map<String, String> params) {
        if (params == null || params.isEmpty()) return path;
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(path);
        params.forEach(builder::queryParam);
        return builder.build().toUriString();
    }

    private String contentType(ResponseEntity<?> resp) {
        MediaType ct = resp.getHeaders().getContentType();
        return ct != null ? ct.toString() : MediaType.APPLICATION_JSON_VALUE;
    }

    private HubResponse errorResponse(HttpStatusCodeException ex) {
        log.warn("ms-hub error {} — {}", ex.getStatusCode().value(), ex.getMessage());
        return new HubResponse(ex.getStatusCode().value(),
                ex.getResponseBodyAsString(),
                MediaType.APPLICATION_JSON_VALUE);
    }
}

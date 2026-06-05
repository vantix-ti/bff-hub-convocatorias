package cl.vantix.hub.bff.domain.model;

/**
 * Respuesta genérica del microservicio ms-hub-convocatorias.
 * El BFF la propaga transparentemente al frontend.
 */
public record HubResponse(int status, String body, String contentType) {
    public boolean isSuccess() { return status >= 200 && status < 300; }
    public boolean isBinary()  { return contentType != null && contentType.contains("application/pdf"); }
}

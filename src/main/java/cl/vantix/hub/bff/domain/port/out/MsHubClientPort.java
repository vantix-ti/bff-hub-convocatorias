package cl.vantix.hub.bff.domain.port.out;

import cl.vantix.hub.bff.domain.model.HubResponse;
import java.util.Map;

/**
 * Puerto de salida: cliente HTTP hacia ms-hub-convocatorias.
 * El BFF delega todas las llamadas al microservicio a través de esta interfaz.
 */
public interface MsHubClientPort {

    HubResponse get(String path, String jwtToken);
    HubResponse get(String path, String jwtToken, Map<String, String> queryParams);
    HubResponse post(String path, Object body, String jwtToken);
    HubResponse put(String path, Object body, String jwtToken);
    HubResponse delete(String path, String jwtToken);

    /** Para descargas binarias (ej: PDF de postulación). */
    HubResponse getBytes(String path, String jwtToken);
}

package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/etapas")
@RequiredArgsConstructor
public class EtapaBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.etapas}")
    private String baseEtapas;

    @Value("${ms.hub.paths.campos}")
    private String baseCampos;

    @Value("${ms.hub.paths.criterios}")
    private String baseCriterios;

    @GetMapping("/{id}")
    public ResponseEntity<String> obtener(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(baseEtapas + "/" + id, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id,
                                              @RequestBody Map<String, Object> body,
                                              HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(baseEtapas + "/" + id, body, BffControllerHelper.extractJwt(req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.delete(baseEtapas + "/" + id, BffControllerHelper.extractJwt(req)));
    }

    // ── Campos ───────────────────────────────────────────────────────────────

    @PostMapping("/{id}/campos")
    public ResponseEntity<String> crearCampo(@PathVariable Long id,
                                              @RequestBody Map<String, Object> body,
                                              HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(baseEtapas + "/" + id + "/campos", body, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/campos/{id}")
    public ResponseEntity<String> actualizarCampo(@PathVariable Long id,
                                                   @RequestBody Map<String, Object> body,
                                                   HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(baseCampos + "/" + id, body, BffControllerHelper.extractJwt(req)));
    }

    @DeleteMapping("/campos/{id}")
    public ResponseEntity<String> eliminarCampo(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.delete(baseCampos + "/" + id, BffControllerHelper.extractJwt(req)));
    }

    // ── Criterios ─────────────────────────────────────────────────────────────

    @PostMapping("/{id}/criterios")
    public ResponseEntity<String> crearCriterio(@PathVariable Long id,
                                                 @RequestBody Map<String, Object> body,
                                                 HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(baseEtapas + "/" + id + "/criterios", body, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/criterios/{id}")
    public ResponseEntity<String> actualizarCriterio(@PathVariable Long id,
                                                      @RequestBody Map<String, Object> body,
                                                      HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(baseCriterios + "/" + id, body, BffControllerHelper.extractJwt(req)));
    }

    @DeleteMapping("/criterios/{id}")
    public ResponseEntity<String> eliminarCriterio(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.delete(baseCriterios + "/" + id, BffControllerHelper.extractJwt(req)));
    }
}

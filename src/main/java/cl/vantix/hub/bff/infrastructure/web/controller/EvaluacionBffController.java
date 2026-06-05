package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.evaluaciones}")
    private String base;

    @PostMapping("/asignar")
    public ResponseEntity<String> asignar(@RequestBody Map<String, Object> body,
                                           HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base + "/asignar", body, BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/mis-evaluaciones")
    public ResponseEntity<String> misEvaluaciones(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/mis-evaluaciones", BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> guardar(@PathVariable Long id,
                                           @RequestBody Map<String, Object> body,
                                           HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/" + id, body, BffControllerHelper.extractJwt(req)));
    }

    @PostMapping("/{id}/finalizar")
    public ResponseEntity<String> finalizar(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base + "/" + id + "/finalizar", null, BffControllerHelper.extractJwt(req)));
    }

    @PostMapping("/etapas/{etapaId}/notificar-resultados")
    public ResponseEntity<String> notificar(@PathVariable Long etapaId, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base + "/etapas/" + etapaId + "/notificar-resultados",
                        null, BffControllerHelper.extractJwt(req)));
    }
}

package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/convocatorias")
@RequiredArgsConstructor
public class ConvocatoriaBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.convocatorias}")
    private String base;

    @GetMapping
    public ResponseEntity<String> listarPublicas() {
        return BffControllerHelper.toResponse(client.get(base, null));
    }

    @GetMapping("/todas")
    public ResponseEntity<String> listarTodas(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/todas", BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtener(@PathVariable Long id) {
        return BffControllerHelper.toResponse(client.get(base + "/" + id, null));
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Map<String, Object> body,
                                         HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base, body, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(@PathVariable Long id,
                                              @RequestBody Map<String, Object> body,
                                              HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/" + id, body, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<String> cambiarEstado(@PathVariable Long id,
                                                 @RequestBody Map<String, Object> body,
                                                 HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/" + id + "/estado", body, BffControllerHelper.extractJwt(req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.delete(base + "/" + id, BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/{id}/etapas")
    public ResponseEntity<String> listarEtapas(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/" + id + "/etapas", BffControllerHelper.extractJwt(req)));
    }

    @PostMapping("/{id}/etapas")
    public ResponseEntity<String> crearEtapa(@PathVariable Long id,
                                              @RequestBody Map<String, Object> body,
                                              HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base + "/" + id + "/etapas", body, BffControllerHelper.extractJwt(req)));
    }
}

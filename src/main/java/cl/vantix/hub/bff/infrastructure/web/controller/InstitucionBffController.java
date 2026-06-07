package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/instituciones") @RequiredArgsConstructor
public class InstitucionBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.instituciones}")
    private String base;

    @GetMapping
    public ResponseEntity<String> listar(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base, BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtener(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/" + id, BffControllerHelper.extractJwt(req)));
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

    @GetMapping("/slug/{slug}")
    public ResponseEntity<String> obtenerPorSlug(@PathVariable String slug, HttpServletRequest req) {
        return BffControllerHelper.toResponse(client.get(base + "/slug/" + slug, null));
    }
}

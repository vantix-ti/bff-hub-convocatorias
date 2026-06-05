package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
public class NotificacionBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.notificaciones}")
    private String base;

    @GetMapping
    public ResponseEntity<String> listar(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<String> leer(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/" + id + "/leer", null, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/leer-todas")
    public ResponseEntity<String> leerTodas(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/leer-todas", null, BffControllerHelper.extractJwt(req)));
    }

    @PostMapping("/masiva")
    public ResponseEntity<String> enviarMasiva(@org.springframework.web.bind.annotation.RequestBody String body, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base + "/masiva", body, BffControllerHelper.extractJwt(req)));
    }
}

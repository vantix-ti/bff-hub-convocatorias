package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/usuarios") @RequiredArgsConstructor
public class UsuarioBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.usuarios}")
    private String base;

    @GetMapping("/me")
    public ResponseEntity<String> perfil(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/me", BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/me")
    public ResponseEntity<String> actualizar(@RequestBody Map<String, Object> body,
                                             HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/me", body, BffControllerHelper.extractJwt(req)));
    }

    /** Cambiar contraseña del usuario autenticado */
    @PutMapping("/me/password")
    public ResponseEntity<String> cambiarPassword(@RequestBody Map<String, Object> body,
                                                  HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/me/password", body, BffControllerHelper.extractJwt(req)));
    }


    /** ADMIN: listar todos los usuarios */
    @GetMapping
    public ResponseEntity<String> listarTodos(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base, BffControllerHelper.extractJwt(req)));
    }

    /** ADMIN: crear usuario con rol específico */
    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Map<String, Object> body,
                                        HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base, body, BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/revisores")
    public ResponseEntity<String> revisores(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/revisores", BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<String> cambiarRol(@PathVariable Long id,
                                             @RequestBody Map<String, Object> body,
                                             HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/" + id + "/rol", body, BffControllerHelper.extractJwt(req)));
    }
}

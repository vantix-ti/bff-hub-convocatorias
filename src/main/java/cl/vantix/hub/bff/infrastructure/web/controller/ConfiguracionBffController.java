package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/configuracion") @RequiredArgsConstructor
public class ConfiguracionBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.configuracion}")
    private String base;

    @GetMapping("/{institucionId}")
    public ResponseEntity<String> obtener(@PathVariable Long institucionId,
                                          HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/" + institucionId, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/{institucionId}")
    public ResponseEntity<String> actualizar(@PathVariable Long institucionId,
                                              @RequestBody Map<String, Object> body,
                                              HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/" + institucionId, body, BffControllerHelper.extractJwt(req)));
    }
}

package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.dashboard}")
    private String base;

    @GetMapping("/convocatoria/{id}")
    public ResponseEntity<String> getDashboard(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/convocatoria/" + id, BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/global")
    public ResponseEntity<String> getDashboardGlobal(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/global", BffControllerHelper.extractJwt(req)));
    }
}

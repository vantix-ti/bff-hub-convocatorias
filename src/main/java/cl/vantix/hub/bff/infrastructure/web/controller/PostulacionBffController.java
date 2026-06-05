package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/postulaciones")
@RequiredArgsConstructor
public class PostulacionBffController {

    private final MsHubClientPort client;

    @Value("${ms.hub.paths.postulaciones}")
    private String base;

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Map<String, Object> body,
                                         HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base, body, BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/mis-postulaciones")
    public ResponseEntity<String> misPostulaciones(HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/mis-postulaciones", BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> obtener(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/" + id, BffControllerHelper.extractJwt(req)));
    }

    @PutMapping("/{id}/respuestas")
    public ResponseEntity<String> guardarRespuesta(@PathVariable Long id,
                                                    @RequestBody Map<String, Object> body,
                                                    HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.put(base + "/" + id + "/respuestas", body, BffControllerHelper.extractJwt(req)));
    }

    @PostMapping("/{id}/enviar")
    public ResponseEntity<String> enviar(@PathVariable Long id, HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.post(base + "/" + id + "/enviar", null, BffControllerHelper.extractJwt(req)));
    }

    @GetMapping("/convocatoria/{convocatoriaId}")
    public ResponseEntity<String> listarPorConvocatoria(@PathVariable Long convocatoriaId,
                                                          HttpServletRequest req) {
        return BffControllerHelper.toResponse(
                client.get(base + "/convocatoria/" + convocatoriaId, BffControllerHelper.extractJwt(req)));
    }

    /** Descarga PDF — convierte bytes a respuesta con Content-Type application/pdf */
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id, HttpServletRequest req) {
        var hub = client.getBytes(base + "/" + id + "/pdf", BffControllerHelper.extractJwt(req));
        byte[] pdf = Base64.getDecoder().decode(hub.body());
        return ResponseEntity.status(hub.status())
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=postulacion-" + id + ".pdf")
                .body(pdf);
    }
}

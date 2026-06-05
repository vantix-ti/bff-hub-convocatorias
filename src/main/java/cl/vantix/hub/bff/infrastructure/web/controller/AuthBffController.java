package cl.vantix.hub.bff.infrastructure.web.controller;

import cl.vantix.hub.bff.domain.model.LoginBffRequest;
import cl.vantix.hub.bff.domain.model.RegisterBffRequest;
import cl.vantix.hub.bff.domain.model.ResetPasswordBffRequest;
import cl.vantix.hub.bff.domain.model.*;
import cl.vantix.hub.bff.domain.port.in.AuthBffUseCase;
import cl.vantix.hub.bff.domain.port.out.CryptoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthBffController {

    private final AuthBffUseCase authUseCase;
    private final CryptoPort cryptoPort;

    /** Retorna la clave pública RSA para que el frontend cifre las contraseñas. */
    @GetMapping("/public-key")
    public ResponseEntity<Map<String, String>> publicKey() {
        return ResponseEntity.ok(Map.of("publicKey", cryptoPort.getPublicKeyBase64()));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginBffRequest request) {
        return BffControllerHelper.toResponse(authUseCase.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterBffRequest request) {
        return BffControllerHelper.toResponse(authUseCase.register(request));
    }

    @GetMapping("/confirmar-email")
    public ResponseEntity<String> confirmarEmail(@RequestParam String token) {
        return BffControllerHelper.toResponse(authUseCase.confirmarEmail(token));
    }

    @PostMapping("/solicitar-reset")
    public ResponseEntity<String> solicitarReset(@RequestBody Map<String, String> body) {
        return BffControllerHelper.toResponse(authUseCase.solicitarReset(body.get("email")));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordBffRequest request) {
        return BffControllerHelper.toResponse(authUseCase.resetPassword(request));
    }
}

package cl.vantix.hub.bff.application.usecase;

import cl.vantix.hub.bff.domain.model.HubResponse;
import cl.vantix.hub.bff.domain.model.LoginBffRequest;
import cl.vantix.hub.bff.domain.model.RegisterBffRequest;
import cl.vantix.hub.bff.domain.model.ResetPasswordBffRequest;
import cl.vantix.hub.bff.domain.model.*;
import cl.vantix.hub.bff.domain.port.in.AuthBffUseCase;
import cl.vantix.hub.bff.domain.port.out.CryptoPort;
import cl.vantix.hub.bff.domain.port.out.MsHubClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthBffUseCaseImpl implements AuthBffUseCase {

    private final MsHubClientPort msHubClient;
    private final CryptoPort crypto;

    @Value("${ms.hub.paths.auth}")
    private String authPath;

    @Override
    public HubResponse login(LoginBffRequest request) {
        String plainPassword = crypto.decrypt(request.passwordEncrypted());
        Map<String, Object> body = Map.of(
                "email", request.email(),
                "password", plainPassword
        );
        return msHubClient.post(authPath + "/login", body, null);
    }

    @Override
    public HubResponse register(RegisterBffRequest request) {
        String plainPassword = crypto.decrypt(request.passwordEncrypted());
        Map<String, Object> body = new HashMap<>();
        body.put("nombre",           request.nombre());
        body.put("apellidoPaterno",  request.apellidoPaterno());
        body.put("apellidoMaterno",  request.apellidoMaterno());
        body.put("email",            request.email());
        body.put("password",         plainPassword);
        body.put("telefono",         request.telefono());
        return msHubClient.post(authPath + "/register", body, null);
    }

    @Override
    public HubResponse confirmarEmail(String token) {
        return msHubClient.get(authPath + "/confirmar-email", null,
                Map.of("token", token));
    }

    @Override
    public HubResponse solicitarReset(String email) {
        return msHubClient.post(authPath + "/solicitar-reset",
                Map.of("email", email), null);
    }

    @Override
    public HubResponse resetPassword(ResetPasswordBffRequest request) {
        String plainPassword = crypto.decrypt(request.passwordEncrypted());
        return msHubClient.post(authPath + "/reset-password",
                Map.of("token", request.token(), "nuevaPassword", plainPassword), null);
    }
}

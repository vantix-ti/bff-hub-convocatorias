package cl.vantix.hub.bff.domain.port.in;

import cl.vantix.hub.bff.domain.model.*;

public interface AuthBffUseCase {
    HubResponse login(LoginBffRequest request);
    HubResponse register(RegisterBffRequest request);
    HubResponse registerGestor(RegisterGestorBffRequest request);
    HubResponse confirmarEmail(String token);
    HubResponse solicitarReset(String email);
    HubResponse resetPassword(ResetPasswordBffRequest request);
}

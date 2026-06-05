package cl.vantix.hub.bff.domain.port.in;

import cl.vantix.hub.bff.domain.model.HubResponse;
import cl.vantix.hub.bff.domain.model.LoginBffRequest;
import cl.vantix.hub.bff.domain.model.RegisterBffRequest;
import cl.vantix.hub.bff.domain.model.ResetPasswordBffRequest;
import cl.vantix.hub.bff.domain.model.*;

/**
 * Puerto de entrada: operaciones de autenticación.
 * El BFF descifra la contraseña antes de delegar al microservicio.
 */
public interface AuthBffUseCase {
    HubResponse login(LoginBffRequest request);
    HubResponse register(RegisterBffRequest request);
    HubResponse confirmarEmail(String token);
    HubResponse solicitarReset(String email);
    HubResponse resetPassword(ResetPasswordBffRequest request);
}

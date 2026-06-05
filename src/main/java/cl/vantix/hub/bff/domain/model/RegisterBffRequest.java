package cl.vantix.hub.bff.domain.model;

/**
 * Petición de registro desde el frontend.
 * La contraseña llega cifrada con la clave pública RSA del BFF.
 */
public record RegisterBffRequest(
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String email,
        String passwordEncrypted,
        String telefono
) {}

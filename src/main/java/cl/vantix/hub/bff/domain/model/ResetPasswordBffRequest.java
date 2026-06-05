package cl.vantix.hub.bff.domain.model;

/**
 * Petición de restablecimiento de contraseña desde el frontend.
 * La nueva contraseña llega cifrada con la clave pública RSA del BFF.
 */
public record ResetPasswordBffRequest(String token, String passwordEncrypted) {}

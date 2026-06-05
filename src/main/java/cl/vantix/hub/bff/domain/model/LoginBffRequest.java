package cl.vantix.hub.bff.domain.model;

/**
 * Petición de login desde el frontend.
 * La contraseña llega cifrada con la clave pública RSA del BFF.
 */
public record LoginBffRequest(String email, String passwordEncrypted) {}

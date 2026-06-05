package cl.vantix.hub.bff.domain.port.out;

/**
 * Puerto de salida: descifrado de datos sensibles.
 * Implementación: RSA-OAEP / SHA-256.
 */
public interface CryptoPort {
    /** Descifra un texto cifrado en Base64 con la clave privada RSA del BFF. */
    String decrypt(String encryptedBase64);
    /** Retorna la clave pública en formato Base64 (DER/X.509) para el frontend. */
    String getPublicKeyBase64();
}

package cl.vantix.hub.bff.infrastructure.crypto;

import cl.vantix.hub.bff.domain.port.out.CryptoPort;
import cl.vantix.hub.bff.infrastructure.config.CryptoProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import java.security.spec.*;
import javax.crypto.spec.*;



/**
 * Adaptador RSA-OAEP/SHA-256.
 * - El frontend cifra la contraseña con la clave pública.
 * - El BFF la descifra con la clave privada.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RsaCryptoAdapter implements CryptoPort {

    private static final String ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    private final CryptoProperties cryptoProps;

    private PrivateKey privateKey;
    private PublicKey  publicKey;

    @PostConstruct
    public void init() throws Exception {
        KeyFactory kf = KeyFactory.getInstance("RSA");

        byte[] privBytes = Base64.getDecoder().decode(cryptoProps.getPrivateKey());
        privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privBytes));

        byte[] pubBytes = Base64.getDecoder().decode(cryptoProps.getPublicKey());
        publicKey = kf.generatePublic(new X509EncodedKeySpec(pubBytes));

        log.info("RsaCryptoAdapter inicializado correctamente.");
    }

    @Override
    public String decrypt(String encryptedBase64) {
        try {
            // Especificar SHA-256 tanto para el hash OAEP como para MGF1
            // Java por defecto usa SHA-1 en MGF1 aunque se diga OAEPWithSHA-256AndMGF1Padding
            OAEPParameterSpec oaepSpec = new OAEPParameterSpec(
                    "SHA-256",
                    "MGF1",
                    new MGF1ParameterSpec("SHA-256"),
                    PSource.PSpecified.DEFAULT
            );
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPPadding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedBase64));
            return new String(decrypted);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "No se pudo descifrar la contraseña: " + e.getMessage(), e);
        }
    }


    @Override
    public String getPublicKeyBase64() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }
}

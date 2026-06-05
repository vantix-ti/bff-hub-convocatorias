package cl.vantix.hub.bff.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bff.crypto")
public class CryptoProperties {
    private String privateKey;
    private String publicKey;
}

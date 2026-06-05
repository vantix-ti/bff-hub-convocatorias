package cl.vantix.hub.bff.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "bff.security")
public class BffSecurityProperties {
    private String apiKey;
    private String headerName = "X-API-Key";
}

package cl.vantix.hub.bff.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "ms.hub")
public class HubBffProperties {
    private String baseUrl;
    private Map<String, String> paths = new HashMap<>();
    private int connectTimeoutMs = 5000;
    private int readTimeoutMs    = 30000;
}

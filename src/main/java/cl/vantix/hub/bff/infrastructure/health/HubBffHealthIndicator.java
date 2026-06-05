package cl.vantix.hub.bff.infrastructure.health;

import cl.vantix.hub.bff.infrastructure.config.HubBffProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component("msHub")
@RequiredArgsConstructor
@Slf4j
public class HubBffHealthIndicator implements HealthIndicator {

    private final RestClient msHubRestClient;
    private final HubBffProperties props;

    @Override
    public Health health() {
        try {
            var resp = msHubRestClient.get()
                    .uri("/actuator/health")
                    .retrieve()
                    .toEntity(String.class);

            if (resp.getStatusCode().is2xxSuccessful()) {
                return Health.up()
                        .withDetail("servicio", "ms-hub-convocatorias")
                        .withDetail("url", props.getBaseUrl())
                        .withDetail("estado", "ACCESIBLE")
                        .build();
            }
            return Health.down()
                    .withDetail("servicio", "ms-hub-convocatorias")
                    .withDetail("httpStatus", resp.getStatusCode().value())
                    .build();

        } catch (Exception ex) {
            log.warn("ms-hub-convocatorias no disponible: {}", ex.getMessage());
            return Health.down()
                    .withDetail("servicio", "ms-hub-convocatorias")
                    .withDetail("url", props.getBaseUrl())
                    .withDetail("error", ex.getMessage())
                    .build();
        }
    }
}

package cl.vantix.hub.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class HubConvocatoriasBffApplication {
    public static void main(String[] args) {
        SpringApplication.run(HubConvocatoriasBffApplication.class, args);
    }
}

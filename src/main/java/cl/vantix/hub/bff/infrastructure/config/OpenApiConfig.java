package cl.vantix.hub.bff.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "bff-hub-convocatorias", version = "1.0",
                 description = "BFF — Hub Convocatorias | Vantix SpA"),
    security = @SecurityRequirement(name = "ApiKey")
)
@SecurityScheme(
    name = "ApiKey",
    type = SecuritySchemeType.APIKEY,
    in   = SecuritySchemeIn.HEADER,
    paramName = "X-API-Key"
)
public class OpenApiConfig {}

package ch.admin.astra.elfa.trotti.config;

import ch.admin.astra.elfa.trotti.Application;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "LicenceCheck Service API",
                description = "The API to start a verification process, load use-cases and poll for verification status. "
        )
)
@Configuration
public class OpenApiConfig {

    @Bean
    GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("API")
                .pathsToMatch("/api/**", "/health/**")
                .packagesToScan(Application.class.getPackageName())
                .build();
    }
}

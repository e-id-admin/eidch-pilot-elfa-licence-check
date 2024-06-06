package ch.admin.astra.elfa.trotti.controller;

import ch.admin.astra.elfa.trotti.api.AppConfigResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/app-config")
@Tag(name = "AppConfig", description = "App Configuration API")
@RequiredArgsConstructor
@Slf4j
class AppConfigController {

    private final Environment environment;
    private final BuildProperties buildProperties;

    @GetMapping
    public AppConfigResponse getConfiguration() {
        return new AppConfigResponse(buildProperties.getVersion(), getEnvironmentName());
    }

    private String getEnvironmentName() {
        if (this.environment.acceptsProfiles(Profiles.of("local"))) {
            return "LOCAL";
        } else if (this.environment.acceptsProfiles(Profiles.of("dev"))) {
            return "DEV";
        } else if (this.environment.acceptsProfiles(Profiles.of("ref"))) {
            return "REF";
        } else if (this.environment.acceptsProfiles(Profiles.of("abn"))) {
            return "ABN";
        } else if (this.environment.acceptsProfiles(Profiles.of("prod"))) {
            return "PROD";
        }
        return "unknown";
    }
}

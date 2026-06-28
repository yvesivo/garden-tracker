package com.gardentracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gardenTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GartenTracker API")
                        .description("REST API für den Hochbeet- und Gemüsegarten-Tracker")
                        .version("1.0.0"));
    }
}

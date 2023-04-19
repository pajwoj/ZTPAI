package ztpai.security;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi usersApi() {
        return GroupedOpenApi.builder()
                .group("ztpai-users")
                .pathsToMatch("/api/users/*")
                .build();
    }

    @Bean
    public GroupedOpenApi stationsApi() {
        return GroupedOpenApi.builder()
                .group("ztpai-stations")
                .pathsToMatch("/api/stations/*")
                .build();
    }
}

package org.mehmood.blogapplicationbackendproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new Components().addSecuritySchemes("BearerAuth", new SecurityScheme()
                        .name("BearerAuth").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))).info(new Info()
                .title("Blog API")
                .version("1.0")
                .description("JWT secured blog backend"));
    }
    @Bean
    public GroupedOpenApi authGroup() {
        return GroupedOpenApi.builder()
                .group("auth")
                .pathsToMatch("/api/auth/**")
                .build();
    }
    @Bean
    public GroupedOpenApi postGroup() {
        return GroupedOpenApi.builder()
                .group("posts")
                .pathsToMatch("/api/post/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userGroup() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi commentGroup() {
        return GroupedOpenApi.builder()
                .group("comments")
                .pathsToMatch("/api/comment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoryGroup() {
        return GroupedOpenApi.builder()
                .group("categories")
                .pathsToMatch("/api/categories/**")
                .build();
    }

}
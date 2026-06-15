package com.animeSite.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Anime Backend API")
                        .version("1.0.0")
                        .description("REST API for browsing trending, seasonal, and searchable anime data sourced from Jikan API (MyAnimeList).")
                        .contact(new Contact()
                                .name("Anime Site")
                                .email("support@animesite.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")));
    }
}

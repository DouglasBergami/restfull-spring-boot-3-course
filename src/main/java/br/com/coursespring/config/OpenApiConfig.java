package br.com.coursespring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpemAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restful API with java 18 and Spring boot 3")
                        .version("v1")
                        .description("Some description about your API")
                        .termsOfService("https://google.com.br")
                        .license(new License()
                                .name("About license")
                                .url("https://google.com.br")
                        )
                );
    }
}

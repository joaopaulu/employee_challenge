package br.com.basis.employee.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Employee Manage API")
                        .description("Desafio FullStack Basis")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("João Paulo Lima")
                                .url("https://www.linkedin.com/in/joaopaulu/")));
    }
}





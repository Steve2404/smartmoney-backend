package com.geekinstitut.smartmoney.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Leonel Steve Jatsa",
                        email = "jatsasteve@gmail.com",
                        url = "https://geekinstitut.com"
                ),
                description = "OpenAPI documentation for Smart Geek Money",
                title = "OpenAPI Specification - Geek Institut",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class OpenApiConfig {
}

package org.app.engihub.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Henry Tech",
                        email = "fakorodehenry@gmail.com"
                ),
                description = "EngiHub API Documentation",
                termsOfService = "Terms of Service",
                version = "1.0",
                license = @License(
                        name = "UNILAG license"
                ),
                title = "EngiHub Application"
        ),
        servers = {
                @Server(
                        description = "Local Development",
                        url = "http://localhost:8080/"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "JWT"
                )
        }

)
@SecurityScheme(
        name = "JWT",
        description = "JWT Token For Authentication/Authorization",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class OpenAPIConfig {
}

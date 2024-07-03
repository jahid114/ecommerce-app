package com.ecommerce.api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                description = "Its the documentation for Ecommerce API",
                contact = @Contact(
                        name = "MD Zanea Alam Jahid",
                        email = "jahidctgcse@gmail.com"
                ),
                version = "1"
        )
)
public class OpenApiDocConfig {
}

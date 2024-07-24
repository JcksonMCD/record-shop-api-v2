package com.northcoders.record_shop_api_v2.swaggerui;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Record Shop API")
                        .version("1.0")
                        .description("API documentation for the Record Shop project"));
    }
}
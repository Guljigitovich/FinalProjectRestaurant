package project.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
//    private static final String API_KEY = "Bearer Token ";

//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .components(new Components()
//                        .addSecuritySchemes(API_KEY, apiKeySecuritySchema()))
//                .info(new Info().title("Gadgetarium"))
//                .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)));
//        // then apply it. If you don't apply it will not be added to the header in cURL
//    }
//    public SecurityScheme apiKeySecuritySchema() {
//        return new SecurityScheme()
//                .name("Auth API")
//                .description("Please, put the token")
//                .in(SecurityScheme.In.HEADER)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("Bearer");
//    }
@Bean
public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
}

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My API")
                .description("API documentation for my project")
                .version("1.0")
                .build();
    }
}

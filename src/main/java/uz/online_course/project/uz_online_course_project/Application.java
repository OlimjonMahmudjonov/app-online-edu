package uz.online_course.project.uz_online_course_project;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Configuration
    public class SwaggerConfig {

        @Bean
        public OpenAPI springOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("Spring 6 Swagger 2 Annotation Example")
                            .description("Spring 6 Swagger Simple Application")
                            .version("${api.version}")
                            .contact(new Contact()
                                    .name("Mahmudjonov Olimjon")
                                    .email("olimjontatu@gmail.com")
                                    .url("https://github.com/OlimjonMahmudjonov"))
                            .license(new License()
                                    .name("Apache 2.0")
                                    .url("http://springdoc.org"))
                            .termsOfService("http://swagger.io/terms/"))
                    .externalDocs(new ExternalDocumentation()
                            .description("SpringShop Wiki Documentation")
                            .url("https://springshop.wiki.github.org/docs"))
                    .servers(List.of(
                            new Server()
                                    .url("http://localhost:8080")
                                    .description("Production")
                    ));/*.addSecurityItem(new SecurityRequirement().addList("basicAuth"))
                    .components((new Components()
                            .addSecuritySchemes("basicAuth", new SecurityScheme()
                                    .name("basicAuth")
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("basic")))

                    )
                    .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                    .components((new Components()
                            .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                    .name("bearerAuth")
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")))
                    );*/
        }
    }


}

package uz.online_course.project.uz_online_course_project;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.online_course.project.uz_online_course_project.telegram.OnlineEdu;


import java.util.List;


@SpringBootApplication
public class Application {

    public static void main(String[] args) throws TelegramApiException {
        //SpringApplication.run(Application.class, args);

        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        OnlineEdu onlineEdu = context.getBean(OnlineEdu.class);


        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(onlineEdu);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
                    ));
        }
    }


}

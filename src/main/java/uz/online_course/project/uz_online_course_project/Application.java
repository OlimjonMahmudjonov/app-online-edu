package uz.online_course.project.uz_online_course_project;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.online_course.project.uz_online_course_project.telegram.OnlineEdu;


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






}

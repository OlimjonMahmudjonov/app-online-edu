package uz.online_course.project.uz_online_course_project.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

  /*  @Bean
    public PasswordEncoder  getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}

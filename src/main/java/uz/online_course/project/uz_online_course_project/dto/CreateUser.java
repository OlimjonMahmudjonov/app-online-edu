package uz.online_course.project.uz_online_course_project.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import uz.online_course.project.uz_online_course_project.enums.GeneralRoles;

@Data
public class CreateUser {
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private GeneralRoles role;
    private Boolean visible = Boolean.TRUE;
    private String telegramChatId;
    private String telegramUserName;

}

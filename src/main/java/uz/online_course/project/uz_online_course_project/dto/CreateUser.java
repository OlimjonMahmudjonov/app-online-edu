package uz.online_course.project.uz_online_course_project.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.online_course.project.uz_online_course_project.enums.GeneralRoles;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateUser {
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private GeneralRoles role;
    private Boolean visible = Boolean.TRUE;
    private Long telegramChatId;
    private String telegramUserName;

}

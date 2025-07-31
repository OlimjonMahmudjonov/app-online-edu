package uz.online_course.project.uz_online_course_project.dto;


import lombok.*;
import uz.online_course.project.uz_online_course_project.enums.GeneralRoles;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateUser {
    private String username;
    private String email;
    private String password;
    private GeneralRoles role;
    private Boolean visible;
    private String telegramChatId;
    private String telegramUserName;

}

package uz.online_course.project.uz_online_course_project.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.online_course.project.uz_online_course_project.enums.GeneralRoles;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CreateUser {

    @NotEmpty(message = "User name  cannot be empty")
    private String username;

    @NotEmpty(message = "Email cannot be empty")
    @Email
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8 , message = "Password size cannot be 7 character ")
    private String password;

    @NotEmpty(message = "General Role  cannot be empty")
    private GeneralRoles role;

    @NotEmpty(message = "Visible cannot be empty")
    private Boolean visible;

    @NotEmpty(message = "Telegram Chat Id  cannot be empty")
    private Long telegramChatId;

    @NotEmpty(message = "Telegram Chat Name  cannot be empty")
    private String telegramUserName;


}

package uz.online_course.project.uz_online_course_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.core.annotations.ParameterObject;
import uz.online_course.project.uz_online_course_project.enums.GeneralRoles;
import uz.online_course.project.uz_online_course_project.enums.GeneralsStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {

    private Long id;
    private String username;
    private String email;
    private GeneralRoles role;
    private GeneralsStatus status;
    private Boolean visible;
    private LocalDateTime createdDate;
    private Long telegramChatId;
    private String telegramUserName;

}

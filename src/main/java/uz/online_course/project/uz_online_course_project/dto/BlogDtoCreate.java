package uz.online_course.project.uz_online_course_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BlogDtoCreate {
    @NotBlank(message = "title is not empty")
    @Size( max = 300, message = " title length max = 300  sigh  ")
    private String title;

    @NotBlank(message = "content is not empty")
    @Size( max = 300, message = " content length max = 300  sigh  ")
    private String content;

    @NotNull(message = "Author Id  is not null")
    private Long authorId;

}

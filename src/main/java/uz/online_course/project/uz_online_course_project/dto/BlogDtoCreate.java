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


    @NotBlank(message = "Title is required")
    @Size(max = 500, message = "Title cannot exceed 500 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 5000, message = "Content cannot exceed 5000 characters")
    private String content;

    @NotNull(message = "Author ID is required")
    private Long authorId;

}

package uz.online_course.project.uz_online_course_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CourseCommentCreateDto {

    @NotBlank(message = "content is not require")
    private String content;

    @NonNull
    private Long courseId;
    @NonNull
    private Long userId;
    @NonNull
    private Long blogId;
}

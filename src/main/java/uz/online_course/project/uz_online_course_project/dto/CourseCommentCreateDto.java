package uz.online_course.project.uz_online_course_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseCommentCreateDto {

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Course ID is required")
    private Long courseId;




}
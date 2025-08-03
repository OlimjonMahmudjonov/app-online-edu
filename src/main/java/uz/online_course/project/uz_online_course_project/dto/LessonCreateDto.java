package uz.online_course.project.uz_online_course_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonCreateDto {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @NotBlank(message = "Video URL cannot be empty")
    private String videoUrl;

    @NotNull(message = "Lesson order cannot be empty")
    private Integer lessonOrder;

    @NotNull(message = "Course ID cannot be empty")
    private Long courseId;
}
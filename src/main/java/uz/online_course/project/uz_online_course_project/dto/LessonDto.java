package uz.online_course.project.uz_online_course_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    private Long id;
    private String title;
    private String content;
    private String videoUrl;
    private Integer lessonOrder;
    private Long courseId;
}
package uz.online_course.project.uz_online_course_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseCommentDto {
    private Long id;
    private String comment;
    private LocalDateTime createdAt;
    private Long userId;
    private String Username;
    private Long courseId;
    private String courseTitle;
    private Long blogId;
    private String blogTitle;


}

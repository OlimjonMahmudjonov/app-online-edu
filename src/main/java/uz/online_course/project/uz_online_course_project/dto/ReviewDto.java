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

public class ReviewDto {

    private Long id;
    private Long userId;
    private String userName;
    private Long courseId;
    private String courseTitle;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

}

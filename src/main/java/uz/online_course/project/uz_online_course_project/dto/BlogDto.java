package uz.online_course.project.uz_online_course_project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BlogDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private  String authorName ;
    private Long authorId;
    private  int commentCount;

}

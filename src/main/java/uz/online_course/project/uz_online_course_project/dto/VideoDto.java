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
public class VideoDto {
    private  Long id;
    private String title;
    private  String originalTitle;
    private  String downloadUrl;
    private  Long size;
    private LocalDateTime createdAt;
    private  Long lessonId;
    private  String lessonTitle;
}

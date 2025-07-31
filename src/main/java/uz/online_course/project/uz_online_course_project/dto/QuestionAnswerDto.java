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
public class QuestionAnswerDto {
    private Long id;
    private String question;
    private String answer;
    private LocalDateTime createdAt;
    private String authName;
    private Long authId;
    private String categoryName;
    private Long categoryId;
    private boolean hasAnswer;
    private Integer likesCount;
}
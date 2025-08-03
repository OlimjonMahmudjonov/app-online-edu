package uz.online_course.project.uz_online_course_project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswerCreate {

    @NotEmpty(message = "Question cannot be empty")
    private String question;

    @NotEmpty(message = "Answer cannot be empty")
    private String answer;

    @NotNull(message = "Auth Id Not cannot Empty ")
    private Long authId;

    @NotNull(message = "Category Id  cannot be empty")
    private Long categoryId;
}
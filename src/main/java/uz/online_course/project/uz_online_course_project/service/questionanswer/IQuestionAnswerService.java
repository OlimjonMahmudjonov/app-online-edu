package uz.online_course.project.uz_online_course_project.service.questionanswer;

import uz.online_course.project.uz_online_course_project.dto.QuestionAnswerCreate;
import uz.online_course.project.uz_online_course_project.dto.QuestionAnswerDto;

import java.util.List;

public interface IQuestionAnswerService {
    QuestionAnswerDto getQuestionAnswerById(Long id);

    void deleteQuestionAnswerById(Long id);

    QuestionAnswerDto updateQuestionAnswer(Long id, QuestionAnswerCreate questionAnswerCreate);

    QuestionAnswerDto createQuestionAnswer(QuestionAnswerCreate questionAnswerCreate);

    List<QuestionAnswerDto> getAllQuestionAnswer();

    List<QuestionAnswerDto> getQuestionAnswersByAnswerAuthId(Long authId);

    List<QuestionAnswerDto> getQuestionAnswersByCategoryId(Long categoryId);

    List<QuestionAnswerDto> getUnansweredQuestions();

    List<QuestionAnswerDto> getAnsweredQuestions();

    List<QuestionAnswerDto> getRecentQuestionsAnswers(int limit);

    boolean existsById(Long id);
}
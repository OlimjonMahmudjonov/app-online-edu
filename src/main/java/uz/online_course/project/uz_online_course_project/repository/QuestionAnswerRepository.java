package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.online_course.project.uz_online_course_project.entity.QuestionAnswer;

import java.util.List;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    List<QuestionAnswer> findByAuthUserIdOrderByCreatedAtDesc(Long authId);

    List<QuestionAnswer> findByCategoryIdOrderByCreatedAtDesc(Long categoryId);

    @Modifying
    @Query("SELECT qa FROM QuestionAnswer qa WHERE qa.answer IS NULL OR qa.answer = '' ORDER BY qa.createdAt DESC")
    List<QuestionAnswer> findUnansweredQuestions();

    @Modifying
    @Query("SELECT qa FROM QuestionAnswer qa WHERE qa.answer IS NOT NULL AND qa.answer != '' ORDER BY qa.createdAt DESC")
    List<QuestionAnswer> findAnsweredQuestions();

    List<QuestionAnswer> findAllByOrderByCreatedAtDesc();

    @Modifying
    @Query("SELECT qa FROM QuestionAnswer qa ORDER BY qa.createdAt DESC")
    List<QuestionAnswer> findRecentQuestionsAnswers(@Param("limit") int limit);

}

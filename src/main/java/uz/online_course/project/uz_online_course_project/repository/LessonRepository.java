package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.online_course.project.uz_online_course_project.entity.Lesson;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    boolean existsByTitleAndContent(String title, String content);

    boolean existsByTitleAndCourseId(String title, Long courseId);

    boolean existsByLessonOrderAndCourseId(Integer lessonOrder, Long courseId);

    Lesson findByTitle(String title);

    List<Lesson> findByCourseId(Long courseId);

    List<Lesson> findByCourseIdAndLessonOrder(Long courseId, Integer lessonOrder);
}

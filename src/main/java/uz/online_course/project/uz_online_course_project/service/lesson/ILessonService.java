package uz.online_course.project.uz_online_course_project.service.lesson;

import uz.online_course.project.uz_online_course_project.dto.LessonUpdateDto;
import uz.online_course.project.uz_online_course_project.entity.Lesson;
import uz.online_course.project.uz_online_course_project.request.AddLesson;

import java.util.List;

public interface ILessonService {

    void deleteLessonById(Long id);

    Lesson addlesson(AddLesson lesson);

    Lesson getLessonById(Long id);

    Lesson updateLesson(LessonUpdateDto lessonUpdateDto, Long lessonId);

    List<Lesson> getLessons();

    Lesson getLessonByTitle(String title);

    List<Lesson> getLessonsByCourseId(Long courseId);

    List<Lesson> getLessonByCourseIdAndderByOrder(Long courseId, Integer lessonOrder);

    boolean existsByTitleAndCourseId(String title, Long courseId);

    boolean existsByOrderAndCourseId(Integer lessonOrder, Long courseId);
}

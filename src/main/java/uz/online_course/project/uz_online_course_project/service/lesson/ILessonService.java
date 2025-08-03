package uz.online_course.project.uz_online_course_project.service.lesson;

import uz.online_course.project.uz_online_course_project.dto.LessonCreateDto;
import uz.online_course.project.uz_online_course_project.dto.LessonDto;
import uz.online_course.project.uz_online_course_project.dto.LessonUpdateDto;
import uz.online_course.project.uz_online_course_project.entity.Lesson;

import java.util.List;

public interface ILessonService {
    void deleteLessonById(Long id);

    LessonDto addLesson(LessonCreateDto lessonCreateDto);

    LessonDto getLessonById(Long id);

    LessonDto updateLesson(LessonUpdateDto lessonUpdateDto, Long lessonId);

    List<LessonDto> getLessons();

    LessonDto getLessonByTitle(String title);

    List<LessonDto> getLessonsByCourseId(Long courseId);

    List<LessonDto> getLessonsByCourseIdAndOrder(Long courseId, Integer lessonOrder);

    boolean existsByTitleAndCourseId(String title, Long courseId);

    boolean existsByOrderAndCourseId(Integer lessonOrder, Long courseId);
}
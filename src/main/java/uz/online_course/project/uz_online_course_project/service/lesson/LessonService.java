package uz.online_course.project.uz_online_course_project.service.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online_course.project.uz_online_course_project.dto.LessonCreateDto;
import uz.online_course.project.uz_online_course_project.dto.LessonDto;
import uz.online_course.project.uz_online_course_project.dto.LessonUpdateDto;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.entity.Lesson;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.CourseRepository;
import uz.online_course.project.uz_online_course_project.repository.LessonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonService implements ILessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    private LessonDto convertToDto(Lesson lesson) {
        LessonDto dto = new LessonDto();
        dto.setId(lesson.getId());
        dto.setTitle(lesson.getTitle());
        dto.setContent(lesson.getContent());
        dto.setVideoUrl(lesson.getVideoUrl());
        dto.setLessonOrder(lesson.getLessonOrder());
        dto.setCourseId(lesson.getCourse().getId());
        return dto;
    }

    private Lesson convertToEntity(LessonCreateDto lessonCreateDto, Course course) {
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonCreateDto.getTitle());
        lesson.setContent(lessonCreateDto.getContent());
        lesson.setVideoUrl(lessonCreateDto.getVideoUrl());
        lesson.setLessonOrder(lessonCreateDto.getLessonOrder());
        lesson.setCourse(course);
        return lesson;
    }

    private void updateEntityFromDto(Lesson existingLesson, LessonUpdateDto lessonUpdateDto) {
        existingLesson.setTitle(lessonUpdateDto.getTitle());
        existingLesson.setContent(lessonUpdateDto.getContent());
        existingLesson.setVideoUrl(lessonUpdateDto.getVideoUrl());
        existingLesson.setLessonOrder(lessonUpdateDto.getLessonOrder());
    }

    @Override
    public void deleteLessonById(Long id) {
        lessonRepository.findById(id).ifPresentOrElse(lessonRepository::delete, () -> {
            throw new ResourceNotFoundException("Lesson ID " + id + " not found");
        });
    }

    @Override
    public LessonDto addLesson(LessonCreateDto lessonCreateDto) {
        if (lessonRepository.existsByTitleAndCourseId(lessonCreateDto.getTitle(), lessonCreateDto.getCourseId())) {
            throw new AlreadyExistsException("Lesson with title '" + lessonCreateDto.getTitle() + "' for course ID " + lessonCreateDto.getCourseId() + " already exists");
        }
        if (lessonRepository.existsByLessonOrderAndCourseId(lessonCreateDto.getLessonOrder(), lessonCreateDto.getCourseId())) {
            throw new AlreadyExistsException("Lesson with order " + lessonCreateDto.getLessonOrder() + " for course ID " + lessonCreateDto.getCourseId() + " already exists");
        }

        Course course = courseRepository.findById(lessonCreateDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course ID " + lessonCreateDto.getCourseId() + " not found"));

        Lesson newLesson = convertToEntity(lessonCreateDto, course);
        Lesson savedLesson = lessonRepository.save(newLesson);
        return convertToDto(savedLesson);
    }

    @Override
    public LessonDto getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson ID " + id + " not found"));
        return convertToDto(lesson);
    }

    @Override
    public LessonDto updateLesson(LessonUpdateDto lessonUpdateDto, Long lessonId) {
        Lesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson ID " + lessonId + " not found"));

        if (!lessonUpdateDto.getTitle().equals(existingLesson.getTitle()) &&
                lessonRepository.existsByTitleAndCourseId(lessonUpdateDto.getTitle(), existingLesson.getCourse().getId())) {
            throw new AlreadyExistsException("Lesson with title '" + lessonUpdateDto.getTitle() + "' for course ID " + existingLesson.getCourse().getId() + " already exists");
        }

        if (!lessonUpdateDto.getLessonOrder().equals(existingLesson.getLessonOrder()) &&
                lessonRepository.existsByLessonOrderAndCourseId(lessonUpdateDto.getLessonOrder(), existingLesson.getCourse().getId())) {
            throw new AlreadyExistsException("Lesson with order " + lessonUpdateDto.getLessonOrder() + " for course ID " + existingLesson.getCourse().getId() + " already exists");
        }

        updateEntityFromDto(existingLesson, lessonUpdateDto);
        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return convertToDto(updatedLesson);
    }

    @Override
    public List<LessonDto> getLessons() {
        return lessonRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LessonDto getLessonByTitle(String title) {
        Lesson lesson = lessonRepository.findByTitle(title);
        if (lesson == null) {
            throw new ResourceNotFoundException("Lesson with title '" + title + "' not found");
        }
        return convertToDto(lesson);
    }

    @Override
    public List<LessonDto> getLessonsByCourseId(Long courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        return lessons.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LessonDto> getLessonsByCourseIdAndOrder(Long courseId, Integer lessonOrder) {
        List<Lesson> lessons = lessonRepository.findByCourseIdAndLessonOrder(courseId, lessonOrder);
        return lessons.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByTitleAndCourseId(String title, Long courseId) {
        return lessonRepository.existsByTitleAndCourseId(title, courseId);
    }

    @Override
    public boolean existsByOrderAndCourseId(Integer lessonOrder, Long courseId) {
        return lessonRepository.existsByLessonOrderAndCourseId(lessonOrder, courseId);
    }
}
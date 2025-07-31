package uz.online_course.project.uz_online_course_project.service.lesson;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.online_course.project.uz_online_course_project.dto.LessonUpdateDto;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.entity.Lesson;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.CourseRepository;
import uz.online_course.project.uz_online_course_project.repository.LessonRepository;
import uz.online_course.project.uz_online_course_project.request.AddLesson;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService implements ILessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public void deleteLessonById(Long id) {
        lessonRepository.findById(id).ifPresentOrElse(lessonRepository::delete, () -> {
            throw new ResourceNotFoundException("Lesson id not found");
        });
    }

    @Override
    public Lesson addlesson(AddLesson lesson) {
        if (lesson.getTitle() == null || lesson.getContent() == null || lesson.getCourseId() == null) {
            throw new AlreadyExistsException("Lesson title or content cannot be empty");
        }
        if (lessonRepository.existsByTitleAndCourseId(lesson.getTitle(), lesson.getCourseId())) {
            throw new AlreadyExistsException("Lesson " + lesson.getTitle() + " and " + lesson.getCourseId() + " already exists");
        }

        Course course = courseRepository.findById(lesson.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course " + lesson.getCourseId() + " not found"));

        Lesson newLesson = modelMapper.map(lesson, Lesson.class);
        newLesson.setCourse(course);

        return lessonRepository.save(newLesson);
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson id not found"));
    }

    @Override
    public Lesson updateLesson(LessonUpdateDto lessonUpdateDto, Long lessonId) {
        if (lessonUpdateDto == null) {
            throw new IllegalArgumentException("Lesson is not empty");
        }
        if (lessonId == null) {
            throw new IllegalArgumentException("Lesson ID is not empty");
        }

        Lesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson ID " + lessonId + " not found"));

        return updateExistingLesson(existingLesson, lessonUpdateDto);
    }

    private Lesson updateExistingLesson(Lesson existingLesson, LessonUpdateDto lesson) {

        if (!lesson.getTitle().equals(existingLesson.getTitle()) &&
                lessonRepository.existsByTitleAndCourseId(lesson.getTitle(), existingLesson.getCourse().getId())) {
            throw new AlreadyExistsException("Course ID " + existingLesson.getCourse().getId() + " for '" + lesson.getTitle() + "' already exists");
        }

        if (!lesson.getOrder().equals(existingLesson.getLessonOrder()) &&
                lessonRepository.existsByLessonOrderAndCourseId(lesson.getOrder(), existingLesson.getCourse().getId())) {
            throw new AlreadyExistsException("Course ID " + existingLesson.getCourse().getId() + " for order " + lesson.getOrder() + " already exists");
        }

        modelMapper.map(lesson, existingLesson);

        return lessonRepository.save(existingLesson);
    }

    @Override
    public List<Lesson> getLessons() {
        return lessonRepository.findAll();
    }

    @Override
    public Lesson getLessonByTitle(String title) {
        return lessonRepository.findByTitle(title);
    }

    @Override
    public List<Lesson> getLessonsByCourseId(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    @Override
    public List<Lesson> getLessonByCourseIdAndderByOrder(Long courseId, Integer lessonOrder) {
        return lessonRepository.findByCourseIdAndLessonOrder(courseId, lessonOrder);
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

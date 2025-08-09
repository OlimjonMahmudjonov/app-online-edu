package uz.online_course.project.uz_online_course_project.service.course;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uz.online_course.project.uz_online_course_project.dto.CourseCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CourseDto;
import uz.online_course.project.uz_online_course_project.dto.CourseUpdateDto;
import uz.online_course.project.uz_online_course_project.enums.GeneralLevel;

import java.util.List;

@Repository
public interface ICourseService {

    CourseDto createCourse(CourseCreateDto courseCreateDto);

    CourseDto updateCourse(Long id, CourseUpdateDto courseUpdateDto);

    boolean deleteCourse(Long id);

    CourseDto getCourseById(Long codeId);

    List<CourseDto> getAllCourses(Pageable pageable);

    List<CourseDto> getAllCoursesByCategoryId(Long categoryId, Pageable pageable);

    List<CourseDto> getAllCoursesByInstructorId(Long instructorId, Pageable pageable);

    List<CourseDto> getRecoursesIsFreeOrIsPayP(boolean isFreeOrIsPayP, Pageable pageable);

    List<CourseDto> getRecoursesByLevel(GeneralLevel generalLevel, Pageable pageable);

    List<CourseDto> getRecoursesByTitle(String title, Pageable pageable);

    List<CourseDto> getCourseWithDiscount(Pageable pageable);

    long getCoursesByInstructorId(Long instructorId);

    long getCoursesByCategoryId(Long categoryId);

    double getAverageRatingByCourseId(Long courseId);

    long getTotalCoursesCount();
}
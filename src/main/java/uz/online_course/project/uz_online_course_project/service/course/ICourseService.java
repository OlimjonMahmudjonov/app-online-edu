package uz.online_course.project.uz_online_course_project.service.course;


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

    List<CourseDto> getAllCourses();

    List<CourseDto> getAllCoursesByCategoryId(Long categoryId);

   // List<CourseDto> getAllCoursesByCourseId(Long courseId);

    List<CourseDto> getAllCoursesByInstructorId(Long instructorId);

    List<CourseDto> getRecoursesIsFreeOrIsPayP(boolean isFreeOrIsPayP);

    List<CourseDto> getRecoursesByLevel(GeneralLevel generalLevel);

    List<CourseDto> getRecoursesByTitle(String title);

    List<CourseDto> getCourseWithDiscount();

    long getCoursesByInstructorId(Long instructorId);

    long getCoursesByCategoryId(Long categoryId);

    double getAverageRatingByCourseId(Long courseId);

    long getTotalCoursesCount();


}

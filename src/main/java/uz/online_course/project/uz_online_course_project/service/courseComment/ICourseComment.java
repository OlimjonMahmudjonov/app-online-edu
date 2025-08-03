package uz.online_course.project.uz_online_course_project.service.courseComment;

import uz.online_course.project.uz_online_course_project.dto.CourseCommentCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CourseCommentDto;

import java.util.List;

public interface ICourseComment {

    CourseCommentDto getCourseCommentById(Long id);

    void deleteCourseCommentById(Long id);

    CourseCommentDto updateCourseComment(Long id, CourseCommentDto commentDto);

    CourseCommentDto createCourseComment(CourseCommentCreateDto commentDto);

    List<CourseCommentDto> getAllCourseComments();

    List<CourseCommentDto> getCommentsByCourseId(Long courseId);

    List<CourseCommentDto> getCommentsByBlogId(Long blogId);

    List<CourseCommentDto> getCommentsByUserId(Long userId);

    List<CourseCommentDto> getRecentComments(Integer limit);

    boolean existsById(Long id);

    Long getCommentsCountByCourseId(Long courseId);

    Long getCommentsCountByBlogId(Long blogId);


}
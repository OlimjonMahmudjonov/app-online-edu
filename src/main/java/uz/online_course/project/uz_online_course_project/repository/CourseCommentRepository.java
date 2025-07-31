package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.online_course.project.uz_online_course_project.entity.CourseComment;

import java.util.List;

public interface CourseCommentRepository extends JpaRepository<CourseComment, Long> {
    List<CourseComment> findByCourseIdOrderByCreatedAtDesc(Long courseId);

    List<CourseComment> findByBlogIdOrderByCreatedAtDesc(Long blogId);

    List<CourseComment> findByUserIdOrderByCreatedAtDesc(Long userId);

    Long countByCourseId(Long courseId);

    // Tuzatilgan method nomi
    Long countByBlogId(Long blogId);
}
package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.enums.GeneralLevel;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCategoryId(Long categoryId);

    // Remove this method or fix the name
    // Course findByCourseId(Long courseId);

    List<Course> findByInstructorId(Long id);  // Fixed typo

    List<Course> findByIsFree(Boolean isFree);

    List<Course> findByLevel(GeneralLevel level);

    List<Course> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT c FROM Course c WHERE c.discountEndDate > :now")
    List<Course> findCoursesWithActiveDiscount(@Param("now") LocalDateTime now);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.instructor.id = :instructorId")
    long countByInstructorId(@Param("instructorId") Long instructorId);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.category.id = :categoryId")
    long countByCategoryId(@Param("categoryId") Long categoryId);

    // Add proper query for average rating
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.course.id = :courseId")
    Double getAverageRatingByCourseId(@Param("courseId") Long courseId);
}
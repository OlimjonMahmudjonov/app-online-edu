package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.enums.GeneralLevel;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Course> findByInstructorId(Long id, Pageable pageable);

    Page<Course> findByIsFree(Boolean isFree, Pageable pageable);

    Page<Course> findByLevel(GeneralLevel level, Pageable pageable);

    Page<Course> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.discountEndDate > :now")
    Page<Course> findCoursesWithActiveDiscount(@Param("now") LocalDateTime now, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.instructor.id = :instructorId")
    long countByInstructorId(@Param("instructorId") Long instructorId);

    @Query("SELECT COUNT(c) FROM Course c WHERE c.category.id = :categoryId")
    long countByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.course.id = :courseId")
    Double getAverageRatingByCourseId(@Param("courseId") Long courseId);



}
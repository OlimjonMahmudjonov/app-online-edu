package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.online_course.project.uz_online_course_project.dto.ReviewDto;
import uz.online_course.project.uz_online_course_project.entity.Review;

@Repository
public interface ReviewRepository  extends JpaRepository<Review,Long> {
    ReviewDto getReviewDtoById(Long reviewId);

}

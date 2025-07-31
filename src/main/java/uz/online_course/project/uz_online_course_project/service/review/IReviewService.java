package uz.online_course.project.uz_online_course_project.service.review;

import org.springframework.data.jpa.repository.Query;
import uz.online_course.project.uz_online_course_project.dto.ReviewDto;

import java.util.List;

public interface IReviewService {

    ReviewDto getReviewDtoById(Long id);

    void deleteReviewDtoById(Long id);

    ReviewDto updateReviewDto(Long id, ReviewDto review);

    ReviewDto createReviewDto(ReviewDto review);

    @Query("SELECT r FROM Review r JOIN FETCH r.course")
    List<ReviewDto> getAllReviewDto();

    List<ReviewDto> getReviewDtoByCourseId(Long courseId);

    List<ReviewDto> getReviewDtoByUserId(Long userId);

    List<ReviewDto> getRecentReviews(Integer limit);

    Double getAverageRating(Long courseId);

    boolean existsReviewDtoById(Long id);

    boolean hasUserReviewedCourseId(Long userId, Long courseId);
}

package uz.online_course.project.uz_online_course_project.service.review;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online_course.project.uz_online_course_project.dto.ReviewCreateDto;
import uz.online_course.project.uz_online_course_project.dto.ReviewDto;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.entity.Review;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.CourseRepository;
import uz.online_course.project.uz_online_course_project.repository.ReviewRepository;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public ReviewDto getReviewDtoById(Long id) {
        return reviewRepository.findById(id).map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    @Override
    public void deleteReviewDtoById(Long id) {
        reviewRepository.findById(id).ifPresentOrElse(
                reviewRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Review not found");
                }
        );
    }


    @Override
    @Transactional(readOnly = true)
    public ReviewDto updateReviewDto(Long id, ReviewDto review) {
        return reviewRepository.findById(id)
                .map(existing -> {
                    if (review.getRating() != null) {
                        existing.setRating(review.getRating());
                    }

                    if (review.getComment() != null && review.getComment().trim().isEmpty()) {
                        existing.setComment(review.getComment());
                    }

                    if (review.getUserId() != null) {
                        User user = userRepository.findById(review.getUserId())
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
                        existing.setUser(user);
                    }

                    if (review.getCourseId() != null) {
                        Course course = courseRepository.findById(review.getCourseId())
                                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
                        existing.setCourse(course);
                    }
                    return convertToDto(reviewRepository.save(existing));
                }).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
    }

    @Override
    public ReviewDto createReviewDto(ReviewDto review) {

        ReviewCreateDto createDto = new ReviewCreateDto();
        createDto.setUserId(review.getUserId());
        createDto.setCourseId(review.getCourseId());
        createDto.setRating(review.getRating());
        createDto.setComment(review.getComment());
        return createReview(createDto);
    }

    private ReviewDto createReview(ReviewCreateDto review) {
        User user = userRepository.findById(review.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Course course = courseRepository.findById(review.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (hasUserReviewedCourseId(review.getUserId(), review.getCourseId())) {
            throw new AlreadyExistsException("User already have reviewed course id");
        }
        final Course finalCourse = course;
        final User finalUser = user;
        return Optional.of(review)
                .map(dto -> {
                    Review reviews = new Review();
                    reviews.setUser(finalUser);
                    reviews.setCourse(finalCourse);
                    reviews.setRating(dto.getRating());
                    reviews.setComment(dto.getComment());
                    reviews.setCreatedAt(LocalDateTime.now());
                    return convertToDto(reviewRepository.save(reviews));
                }).orElseThrow(() -> new RuntimeException("Failed to save review"));

    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getAllReviewDto() {
        return reviewRepository.findAll()
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getReviewDtoByCourseId(Long courseId) {
        courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return reviewRepository.findAll()
                .stream()
                .filter(review -> review.getCourse() != null
                        && review.getCourse().getId().equals(courseId))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewDtoByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return reviewRepository.findAll()
                .stream()
                .filter(review -> review.getUser().getId().equals(userId))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewDto> getRecentReviews(Integer limit) {
        return reviewRepository.findAll()
                .stream()
                .sorted((time_1, time_2) -> time_2.getCreatedAt().compareTo(time_1.getCreatedAt()))
                .limit(limit)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

        @Override
        public Double getAverageRating(Long courseId) {
            courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

            List<Review> courseReviews = reviewRepository.findAll()
                    .stream()
                    .filter(review -> review.getCourse().getId().equals(courseId))
                    .toList();

            if (courseReviews.isEmpty()) {
                return 0.0;
            }

            double average = courseReviews
                    .stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            return Math.round(average * 100.0) / 100.0;
        }

    @Override
    public boolean existsReviewDtoById(Long id) {
        return reviewRepository.existsById(id);
    }

    @Override
    public boolean hasUserReviewedCourseId(Long userId, Long courseId) {
        return reviewRepository.findAll()
                .stream()
                .anyMatch(review -> review.getUser().getId().equals(userId) &&
                        review.getCourse().getId().equals(courseId));
    }


    public ReviewDto convertToDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getUsername());
        dto.setCourseId(review.getCourse().getId());
        dto.setCourseTitle(review.getCourse().getTitle());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }

}

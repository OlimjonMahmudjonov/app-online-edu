package uz.online_course.project.uz_online_course_project.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.ReviewDto;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.review.IReviewService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor

public class ReviewController {
    private final IReviewService reviewService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getReviewById(@PathVariable("id") Long reviewId) {
        try {
            ReviewDto reviewDto = reviewService.getReviewDtoById(reviewId);
            return ResponseEntity.ok(new ApiResponse("Review retrieved successfully", reviewDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Internal server error", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createReview(@RequestBody ReviewDto reviewDto) {
        try {
            ReviewDto createReviewDto = reviewService.createReviewDto(reviewDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Review created successfully" , createReviewDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateReview(@PathVariable("id") Long reviewId, @RequestBody ReviewDto reviewDto) {
        try {
            ReviewDto updateReviewDto = reviewService.updateReviewDto(reviewId, reviewDto);
            return ResponseEntity.ok(new ApiResponse("Review updated successfully", updateReviewDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Concurrent update conflict: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error", null));
        }
    }

    @DeleteMapping("/delete/{id}")

    public ResponseEntity<ApiResponse> deleteReview(@PathVariable("id") Long reviewId) {

        try {
            reviewService.deleteReviewDtoById(reviewId);
            return ResponseEntity.ok(new ApiResponse("Review Delete successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Internal server error", null));
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllReviews() {
        try {
            List<ReviewDto> reviewDtos = reviewService.getAllReviewDto();
            return ResponseEntity.ok(new ApiResponse("Reviews retrieved successfully", reviewDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error", null));
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse> getReviewDtoByCourseId(@PathVariable Long courseId) {
        try {
            List<ReviewDto> reviewDtos = reviewService.getReviewDtoByCourseId(courseId);
            return ResponseEntity.ok(new ApiResponse("Review retrieved successfully", reviewDtos));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getReviewDtoByUserId(@PathVariable Long userId) {
        List<ReviewDto> reviewDtos = reviewService.getReviewDtoByUserId(userId);
        return ResponseEntity.ok(new ApiResponse("Review retrieved successfully", reviewDtos));

    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse> getRecentReviews(@RequestParam Integer limit) {
        List<ReviewDto> reviewDtos = reviewService.getRecentReviews(limit);
        return ResponseEntity.ok(new ApiResponse("Review retrieved successfully", reviewDtos));

    }

    @GetMapping("/course/{courseId}/average-rating")
    public ResponseEntity<ApiResponse> getAverageRating(@PathVariable Long courseId) {
        Double averageRating = reviewService.getAverageRating(courseId);
        return ResponseEntity.ok(new ApiResponse("Review retrieved successfully", averageRating));
    }

    @GetMapping("/{id}/exist")
    public ResponseEntity<ApiResponse> existsReviewDtoById(@PathVariable Long id) {
        boolean exists = reviewService.existsReviewDtoById(id);
        return ResponseEntity.ok(new ApiResponse("Review retrieved successfully", exists));
    }

    @GetMapping("/user/{userId}/course/{courseId}/review")
    public ResponseEntity<ApiResponse> hasUserReviewedCourseId(@PathVariable Long userId, @PathVariable Long courseId) {
        try {
            boolean hasReview = reviewService.hasUserReviewedCourseId(userId, courseId);
            return ResponseEntity.ok(new ApiResponse("Review retrieved successfully", hasReview));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}

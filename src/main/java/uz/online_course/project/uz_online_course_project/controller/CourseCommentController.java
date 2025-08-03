package uz.online_course.project.uz_online_course_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.CourseCommentCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CourseCommentDto;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.courseComment.ICourseComment;

import java.util.List;

@RestController
@RequestMapping("/api/course-comments")
@RequiredArgsConstructor

public class CourseCommentController {

    private final ICourseComment courseCommentService;

    @PostMapping
    public ResponseEntity<ApiResponse> createComment(@Valid @RequestBody CourseCommentCreateDto commentDto) {
        CourseCommentDto createdComment = courseCommentService.createCourseComment(commentDto);
        return new ResponseEntity<>(new ApiResponse("Comment created successfully", createdComment), HttpStatus.CREATED);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<ApiResponse> getCommentById(@PathVariable Long id) {
        CourseCommentDto comment = courseCommentService.getCourseCommentById(id);
        return ResponseEntity.ok(new ApiResponse("Comment retrieved successfully", comment));
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllComments() {
        List<CourseCommentDto> comments = courseCommentService.getAllCourseComments();
        return ResponseEntity.ok(new ApiResponse("All comments retrieved successfully", comments));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse> getCommentsByCourse(@PathVariable Long courseId) {
        List<CourseCommentDto> comments = courseCommentService.getCommentsByCourseId(courseId);
        return ResponseEntity.ok(new ApiResponse("Course comments retrieved successfully", comments));
    }

    @GetMapping("/blog/{blogId}")
    public ResponseEntity<ApiResponse> getCommentsByBlog(@PathVariable Long blogId) {
        List<CourseCommentDto> comments = courseCommentService.getCommentsByBlogId(blogId);
        return ResponseEntity.ok(new ApiResponse("Blog comments retrieved successfully", comments));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getCommentsByUser(@PathVariable Long userId) {
        List<CourseCommentDto> comments = courseCommentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(new ApiResponse("User comments retrieved successfully", comments));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse> getRecentComments(@RequestParam(defaultValue = "10") Integer limit) {
        List<CourseCommentDto> comments = courseCommentService.getRecentComments(limit);
        return ResponseEntity.ok(new ApiResponse("Recent comments retrieved successfully", comments));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable Long id,
                                                     @Valid @RequestBody CourseCommentDto commentDto) {
        CourseCommentDto updatedComment = courseCommentService.updateCourseComment(id, commentDto);
        return ResponseEntity.ok(new ApiResponse("Comment updated successfully", updatedComment));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long id) {
        courseCommentService.deleteCourseCommentById(id);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully", null));
    }

    @GetMapping("/course/{courseId}/count")
    public ResponseEntity<ApiResponse> getCommentsCountByCourse(@PathVariable Long courseId) {
        Long count = courseCommentService.getCommentsCountByCourseId(courseId);
        return ResponseEntity.ok(new ApiResponse("Course comments count retrieved successfully", count));
    }

    @GetMapping("/blog/{blogId}/count")
    public ResponseEntity<ApiResponse> getCommentsCountByBlog(@PathVariable Long blogId) {
        Long count = courseCommentService.getCommentsCountByBlogId(blogId);
        return ResponseEntity.ok(new ApiResponse("Blog comments count retrieved successfully", count));
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<ApiResponse> checkCommentExists(@PathVariable Long id) {
        boolean exists = courseCommentService.existsById(id);
        return ResponseEntity.ok(new ApiResponse("Comment existence checked successfully", exists));
    }
}
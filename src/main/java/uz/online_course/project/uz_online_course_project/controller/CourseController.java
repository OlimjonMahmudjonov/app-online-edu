package uz.online_course.project.uz_online_course_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.CourseCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CourseDto;
import uz.online_course.project.uz_online_course_project.dto.CourseUpdateDto;
import uz.online_course.project.uz_online_course_project.enums.GeneralLevel;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.course.ICourseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final ICourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CourseCreateDto courseCreateDto) {
        try {
            CourseDto courseDto = courseService.createCourse(courseCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Course created successfully", courseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDto courseUpdateDto) {
        try {
            CourseDto courseDto = courseService.updateCourse(id, courseUpdateDto);
            return ResponseEntity.ok(new ApiResponse("Course updated successfully", courseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long id) {
        try {
            boolean deleteCourse = courseService.deleteCourse(id);
            return ResponseEntity.ok(new ApiResponse("Course deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error: " + e.getMessage(), null));
        }
    }

    @GetMapping("/get/course/{id}")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable Long id) {
        try {
            CourseDto courseDto = courseService.getCourseById(id);
            return ResponseEntity.ok(new ApiResponse("Course found successfully", courseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllCourses(Pageable pageable) {
        List<CourseDto> courseDtos = courseService.getAllCourses(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("courses", courseDtos);
        response.put("totalItems", courseService.getTotalCoursesCount());
        response.put("totalPages", (long) Math.ceil((double) courseService.getTotalCoursesCount() / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Courses found successfully", response));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getAllCoursesByCategoryId(@PathVariable Long categoryId, Pageable pageable) {
        List<CourseDto> courseDtos = courseService.getAllCoursesByCategoryId(categoryId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("courses", courseDtos);
        response.put("totalItems", courseService.getCoursesByCategoryId(categoryId));
        response.put("totalPages", (long) Math.ceil((double) courseService.getCoursesByCategoryId(categoryId) / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Courses found successfully", response));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<ApiResponse> getAllCoursesByInstructorId(@PathVariable Long instructorId, Pageable pageable) {
        List<CourseDto> courseDtos = courseService.getAllCoursesByInstructorId(instructorId, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("courses", courseDtos);
        response.put("totalItems", courseService.getCoursesByInstructorId(instructorId));
        response.put("totalPages", (long) Math.ceil((double) courseService.getCoursesByInstructorId(instructorId) / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Courses found successfully", response));
    }

    @GetMapping("/free-or-paid")
    public ResponseEntity<ApiResponse> getRecoursesIsFreeOrIsPayP(@RequestParam boolean isFreeOrIsPayP, Pageable pageable) {
        List<CourseDto> courseDtos = courseService.getRecoursesIsFreeOrIsPayP(isFreeOrIsPayP, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("courses", courseDtos);
        response.put("totalItems", courseService.getTotalCoursesCount());
        response.put("totalPages", (long) Math.ceil((double) courseService.getTotalCoursesCount() / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Courses found successfully", response));
    }

    @GetMapping("/level")
    public ResponseEntity<ApiResponse> getRecoursesByLevel(@RequestParam GeneralLevel generalLevel, Pageable pageable) {
        List<CourseDto> courseDtos = courseService.getRecoursesByLevel(generalLevel, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("courses", courseDtos);
        response.put("totalItems", courseService.getTotalCoursesCount());
        response.put("totalPages", (long) Math.ceil((double) courseService.getTotalCoursesCount() / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Courses found successfully", response));
    }

    @GetMapping("/find-title")
    public ResponseEntity<ApiResponse> getRecoursesByTitle(@RequestParam String title, Pageable pageable) {
        List<CourseDto> courseDtos = courseService.getRecoursesByTitle(title, pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("courses", courseDtos);
        response.put("totalItems", courseService.getTotalCoursesCount());
        response.put("totalPages", (long) Math.ceil((double) courseService.getTotalCoursesCount() / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Courses found successfully", response));
    }

    @GetMapping("/discount")
    public ResponseEntity<ApiResponse> getCourseWithDiscount(Pageable pageable) {
        List<CourseDto> courseDtos = courseService.getCourseWithDiscount(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("courses", courseDtos);
        response.put("totalItems", courseService.getTotalCoursesCount());
        response.put("totalPages", (long) Math.ceil((double) courseService.getTotalCoursesCount() / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Courses with discount retrieved successfully", response));
    }

    @GetMapping("/instructor/{instructorId}/count")
    public ResponseEntity<ApiResponse> getCoursesByInstructorId(@PathVariable Long instructorId) {
        long count = courseService.getCoursesByInstructorId(instructorId);
        return ResponseEntity.ok(new ApiResponse("Courses count retrieved successfully", count));
    }

    @GetMapping("/category/{categoryId}/count")
    public ResponseEntity<ApiResponse> getCoursesByCategoryId(@PathVariable Long categoryId) {
        long count = courseService.getCoursesByCategoryId(categoryId);
        return ResponseEntity.ok(new ApiResponse("Courses count retrieved successfully", count));
    }

    @GetMapping("/{courseId}/rating")
    public ResponseEntity<ApiResponse> getAverageRatingByCourseId(@PathVariable Long courseId) {
        try {
            double averageRating = courseService.getAverageRatingByCourseId(courseId);
            return ResponseEntity.ok(new ApiResponse("Average rating retrieved successfully", averageRating));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getTotalCoursesCount() {
        long count = courseService.getTotalCoursesCount();
        return ResponseEntity.ok(new ApiResponse("Courses count retrieved successfully", count));
    }
}
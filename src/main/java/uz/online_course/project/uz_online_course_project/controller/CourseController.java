package uz.online_course.project.uz_online_course_project.controller;


import lombok.RequiredArgsConstructor;
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

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor

public class CourseController {
    private final ICourseService courseService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CourseCreateDto courseCreateDto) {
        CourseDto courseDto = courseService.createCourse(courseCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Course created successfully", courseDto));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDto courseUpdateDto) {

            CourseDto courseDto = courseService.updateCourse(id, courseUpdateDto);
            return ResponseEntity.ok(new ApiResponse("Course updated successfully", courseDto));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long id) {
        try {
            boolean deleteCourse = courseService.deleteCourse(id);
            return ResponseEntity.ok(new ApiResponse("Course deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Internal server error", null));
        }
    }

    @GetMapping("/get/course/{id}")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable Long id) {
        CourseDto courseDto = courseService.getCourseById(id);
        return ResponseEntity.ok(new ApiResponse("Course found successfully", courseDto));
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllCourses() {
        List<CourseDto> courseDtos = courseService.getAllCourses();
        return ResponseEntity.ok(new ApiResponse("Course found successfully", courseDtos));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getAllCoursesByCategoryId(@RequestParam Long categoryId) {
        List<CourseDto> courseDtos = courseService.getAllCoursesByCategoryId(categoryId);
        return ResponseEntity.ok(new ApiResponse("Course found successfully", courseDtos));
    }

    @GetMapping("/instructor/{instructorId}")
    public ResponseEntity<ApiResponse> getAllCoursesByInstructorId(@PathVariable Long instructorId) {
        List<CourseDto> courseDtos = courseService.getAllCoursesByInstructorId(instructorId);
        return ResponseEntity.ok(new ApiResponse("Course found successfully", courseDtos));
    }

    @GetMapping("/free-or-paid")
    public ResponseEntity<ApiResponse> getRecoursesIsFreeOrIsPayP(@RequestParam boolean isFreeOrIsPayP) {
        List<CourseDto> courseDtos = courseService.getRecoursesIsFreeOrIsPayP(isFreeOrIsPayP);
        return ResponseEntity.ok(new ApiResponse("Course found successfully", courseDtos));
    }

    @GetMapping("/level")
    public ResponseEntity<ApiResponse> getRecoursesByLevel(@RequestParam GeneralLevel generalLevel) {
        List<CourseDto> courseDtos = courseService.getRecoursesByLevel(generalLevel);
        return ResponseEntity.ok(new ApiResponse("Course found successfully", courseDtos));
    }

    @GetMapping("/find-title")
    public ResponseEntity<ApiResponse> getRecoursesByTitle(@RequestParam String title) {
        List<CourseDto> courseDtos = courseService.getRecoursesByTitle(title);
        return ResponseEntity.ok(new ApiResponse("Course found successfully", courseDtos));
    }

    @GetMapping("/discount")
    public ResponseEntity<ApiResponse> getCourseWithDiscount() {
        List<CourseDto> courseDtos = courseService.getCourseWithDiscount();
        return ResponseEntity.ok(new ApiResponse("Courses with discount retrieved successfully", courseDtos));
    }

    @GetMapping("/instructor/{instructorId}/count")
    public ResponseEntity<ApiResponse> getCoursesByInstructorId(@PathVariable Long instructorId) {
        long count = courseService.getCoursesByInstructorId(instructorId);
        return ResponseEntity.ok(new ApiResponse("Course found successfully", count));
    }


    @GetMapping("category/{categoryId}/count")
    public  ResponseEntity<ApiResponse> getCoursesByCategoryId ( @PathVariable Long categoryId) {
        long count = courseService.getCoursesByCategoryId(categoryId);
        return ResponseEntity.ok(new ApiResponse("Course found successfully", count));
    }


    @GetMapping("/{courseId}/rating")
    public  ResponseEntity<ApiResponse> getAverageRatingByCourseId (@PathVariable Long courseId) {
        try {
            double  averageRating = courseService.getAverageRatingByCourseId(courseId);
            return ResponseEntity.ok(new ApiResponse("Course found successfully", averageRating));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count")
    public  ResponseEntity<ApiResponse> getTotalCoursesCount (){
        long count = courseService.getTotalCoursesCount();
        return ResponseEntity.ok(new ApiResponse("Course found successfully", count));
    }




}

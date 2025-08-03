package uz.online_course.project.uz_online_course_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.LessonCreateDto;
import uz.online_course.project.uz_online_course_project.dto.LessonDto;
import uz.online_course.project.uz_online_course_project.dto.LessonUpdateDto;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.lesson.ILessonService;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor

public class LessonController {
    private final ILessonService lessonService;

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteLessonById(@PathVariable Long id) {
        try {
            lessonService.deleteLessonById(id);
            return ResponseEntity.ok(new ApiResponse("Lesson deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Lesson not found", null));
        }
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR' ,'ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addLesson(@Valid @RequestBody LessonCreateDto lessonCreateDto) {
        try {
            LessonDto lessonDto = lessonService.addLesson(lessonCreateDto);
            return new ResponseEntity<>(new ApiResponse("Lesson created successfully", lessonDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error", null));
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getLessonById(@PathVariable Long id) {
        try {
            LessonDto lessonDto = lessonService.getLessonById(id);
            return ResponseEntity.ok(new ApiResponse("Lesson retrieved successfully", lessonDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Lesson not found", null));
        }
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR' ,'ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateLesson(@PathVariable Long id, @Valid @RequestBody LessonUpdateDto lessonUpdateDto) {
        try {
            LessonDto updatedLessonDto = lessonService.updateLesson(lessonUpdateDto, id);
            return ResponseEntity.ok(new ApiResponse("Lesson updated successfully", updatedLessonDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Lesson not found", null));
        }
    }


    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getLessons() {
        List<LessonDto> lessons = lessonService.getLessons();
        return ResponseEntity.ok(new ApiResponse("Lessons retrieved successfully", lessons));
    }


    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse> getLessonByTitle(@RequestParam String title) {
        try {
            LessonDto lessonDto = lessonService.getLessonByTitle(title);
            return ResponseEntity.ok(new ApiResponse("Lesson retrieved successfully", lessonDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Lesson not found title ", null));
        }
    }


    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse> getLessonsByCourseId(@PathVariable Long courseId) {
        try {
            List<LessonDto> lessons = lessonService.getLessonsByCourseId(courseId);
            return ResponseEntity.ok(new ApiResponse("Lessons retrieved successfully", lessons));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Lesson not found", null));
        }
    }


    @GetMapping("/course/{courseId}/order/{lessonOrder}")
    public ResponseEntity<ApiResponse> getLessonsByCourseIdAndOrder(@PathVariable Long courseId, @PathVariable Integer lessonOrder) {
        List<LessonDto> lessons = lessonService.getLessonsByCourseIdAndOrder(courseId, lessonOrder);
        return ResponseEntity.ok(new ApiResponse("Lessons retrieved successfully", lessons));
    }


    @GetMapping("/exists/title")
    public ResponseEntity<ApiResponse> existsByTitleAndCourseId(@RequestParam String title, @RequestParam Long courseId) {
        try {
            boolean exists = lessonService.existsByTitleAndCourseId(title, courseId);
            return ResponseEntity.ok(new ApiResponse("Lesson title existence checked successfully", exists));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Lesson not found", null));
        }
    }


    @GetMapping("/exists/order")
    public ResponseEntity<ApiResponse> existsByOrderAndCourseId(@RequestParam Integer lessonOrder, @RequestParam Long courseId) {
        boolean exists = lessonService.existsByOrderAndCourseId(lessonOrder, courseId);
        return ResponseEntity.ok(new ApiResponse("Lesson order existence checked successfully", exists));
    }
}
package uz.online_course.project.uz_online_course_project.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.LessonUpdateDto;
import uz.online_course.project.uz_online_course_project.entity.Lesson;
import uz.online_course.project.uz_online_course_project.request.AddLesson;
import uz.online_course.project.uz_online_course_project.request.UpdateLesson;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.lesson.ILessonService;

import java.util.List;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final ILessonService lessonService;

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLessonById(@PathVariable Long id) {
        lessonService.deleteLessonById(id);
        return ResponseEntity.ok(new ApiResponse("delete lesson ", null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addlesson(@RequestBody AddLesson lesson) {
        Lesson lessonCreate = lessonService.addlesson(lesson);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("add lesson ", lessonCreate));

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getLessonById(@PathVariable Long id) {
        Lesson lesson = lessonService.getLessonById(id);
        return ResponseEntity.ok(new ApiResponse("get lesson ", lesson));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateLesson(@PathVariable Long id, @RequestBody LessonUpdateDto lessonUpdateDto) {
        Lesson updatedLesson = lessonService.updateLesson(lessonUpdateDto, id);
        return ResponseEntity.ok(new ApiResponse("Lesson updated successfully", updatedLesson));
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getLessons() {
        List<Lesson> lessons = lessonService.getLessons();
        return ResponseEntity.ok(new ApiResponse("get lessons ", lessons));
    }

    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse> getLessonByTitle(@RequestParam String title) {
        Lesson lesson = lessonService.getLessonByTitle(title);
        return ResponseEntity.ok(new ApiResponse("get lesson ", lesson));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse> getLessonsByCourseId(@PathVariable Long courseId) {
        List<Lesson> lessons = lessonService.getLessonsByCourseId(courseId);
        return ResponseEntity.ok(new ApiResponse("get lessons ", lessons));
    }

    @GetMapping("/course/{courseId}/order")
    public ResponseEntity<ApiResponse> getLessonByCourseIdAndderByOrder(@PathVariable Long courseId, @RequestParam Integer lessonOrder) {

        List<Lesson> lessons = lessonService.getLessonByCourseIdAndderByOrder(courseId, lessonOrder);
        return ResponseEntity.ok(new ApiResponse("get lessons ", lessons));
    }

    @GetMapping("/exist/title")
    public ResponseEntity<ApiResponse> existsByTitleAndCourseId(@RequestParam String title, @PathVariable Long courseId) {
        boolean exists = lessonService.existsByTitleAndCourseId(title, courseId);
        return ResponseEntity.ok(new ApiResponse("Lesson title existence check completed ", exists));
    }


    @GetMapping("/exist/order")
    public ResponseEntity<ApiResponse> existsByOrderAndCourseId(@RequestParam Integer lessonOrder, @PathVariable Long courseId) {
        boolean exists = lessonService.existsByOrderAndCourseId(lessonOrder, courseId);
        return ResponseEntity.ok(new ApiResponse("Lesson order existence check completed", exists));
    }

}

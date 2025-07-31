package uz.online_course.project.uz_online_course_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.online_course.project.uz_online_course_project.dto.CourseCommentDto;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.category.ICategoryService;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CourseCommentController {
    private  final ICategoryService categoryService;


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCourseCommentById (@PathVariable Long id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("category delete" , null));
    }

    public  ResponseEntity<ApiResponse> updateCourseComment (@PathVariable Long id, CourseCommentDto commentDto){
        return null;
    }
}

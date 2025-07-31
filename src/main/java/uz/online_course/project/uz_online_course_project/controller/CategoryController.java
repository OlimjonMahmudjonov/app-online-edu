package uz.online_course.project.uz_online_course_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.CategoryCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CategoryDto;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.category.ICategoryService;

import java.util.List;

@RestController
@RequestMapping("/${api.prefix}/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResponse> createCreateCategoryDto(@RequestBody CategoryCreateDto categoryCreateDto) {
        CategoryDto categoryDto = categoryService.createCreateCategoryDto(categoryCreateDto);
        return ResponseEntity.ok(new ApiResponse("Category created successfully", categoryDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> createUpdateCategoryDto(@PathVariable Long id, @RequestBody CategoryCreateDto categoryCreateDto) {
        CategoryDto categoryDto = categoryService.createUpdateCategoryDto(id, categoryCreateDto);
        return ResponseEntity.ok(new ApiResponse("Category created successfully", categoryDto));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Category created successfully", categoryDto));

    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getCategories() {
        List<CategoryDto> categoryDtos = categoryService.getCategories();
        return ResponseEntity.ok(new ApiResponse("Categories created successfully", categoryDtos));
    }

    @GetMapping("/category-and-course")
    public ResponseEntity<ApiResponse> getAllCategoriesAndCourses() {
        List<CategoryDto> categoryDtos = categoryService.getAllCategoriesAndCourses();
        return ResponseEntity.ok(new ApiResponse("Categories created successfully", categoryDtos));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
        boolean deleteCategoryById = categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));

    }

    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse> serchCategoryByName(@RequestParam String name) {
        List<CategoryDto> categoryDtos = categoryService.serchCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse("Categories created successfully", categoryDtos));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getCategoryCount() {
        long count = categoryService.getCategoryCount();
        return ResponseEntity.ok(new ApiResponse("Categories created successfully", count));
    }


}

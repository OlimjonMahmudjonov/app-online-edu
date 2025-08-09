package uz.online_course.project.uz_online_course_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.CategoryCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CategoryDto;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.category.ICategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCreateCategoryDto(@RequestBody CategoryCreateDto categoryCreateDto) {
        try {
            CategoryDto categoryDto = categoryService.createCreateCategoryDto(categoryCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Category created successfully", categoryDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> createUpdateCategoryDto(@PathVariable Long id, @RequestBody CategoryCreateDto categoryCreateDto) {
        try {
            CategoryDto categoryDto = categoryService.createUpdateCategoryDto(id, categoryCreateDto);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try {
            CategoryDto categoryDto = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category found successfully", categoryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getCategories(Pageable pageable) {
        List<CategoryDto> categoryDtos = categoryService.getCategories(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryDtos);
        response.put("totalItems", categoryService.getCategoryCount());
        response.put("totalPages", (long) Math.ceil((double) categoryService.getCategoryCount() / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Categories found successfully", response));
    }

    @GetMapping("/category-and-course")
    public ResponseEntity<ApiResponse> getAllCategoriesAndCourses(Pageable pageable) {
        List<CategoryDto> categoryDtos = categoryService.getAllCategoriesAndCourses(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("categories", categoryDtos);
        response.put("totalItems", categoryService.getCategoryCount());
        response.put("totalPages", (long) Math.ceil((double) categoryService.getCategoryCount() / pageable.getPageSize()));
        response.put("currentPage", pageable.getPageNumber());
        return ResponseEntity.ok(new ApiResponse("Categories found successfully", response));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<ApiResponse> serchCategoryByName(@RequestParam String name) {
        List<CategoryDto> categoryDtos = categoryService.serchCategoryByName(name);
        return ResponseEntity.ok(new ApiResponse("Categories found successfully", categoryDtos));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getCategoryCount() {
        long count = categoryService.getCategoryCount();
        return ResponseEntity.ok(new ApiResponse("Categories count retrieved successfully", count));
    }
}
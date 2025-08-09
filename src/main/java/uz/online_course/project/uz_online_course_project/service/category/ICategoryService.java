package uz.online_course.project.uz_online_course_project.service.category;

import org.springframework.data.domain.Pageable;
import uz.online_course.project.uz_online_course_project.dto.CategoryCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {

    CategoryDto createCreateCategoryDto(CategoryCreateDto categoryCreateDto);

    CategoryDto createUpdateCategoryDto(Long id, CategoryCreateDto categoryCreateDto);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getCategories(Pageable pageable);

    List<CategoryDto> getAllCategoriesAndCourses(Pageable pageable);

    void deleteCategoryById(Long id);

    boolean existsCategoryName(String name);

    List<CategoryDto> serchCategoryByName(String name);

    Long getCategoryCount();
}
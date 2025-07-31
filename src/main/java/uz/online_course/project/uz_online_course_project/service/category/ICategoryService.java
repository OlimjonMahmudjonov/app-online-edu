package uz.online_course.project.uz_online_course_project.service.category;

import uz.online_course.project.uz_online_course_project.dto.CategoryCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {

    CategoryDto createCreateCategoryDto(CategoryCreateDto categoryCreateDto);

    CategoryDto createUpdateCategoryDto(Long id, CategoryCreateDto categoryCreateDto);

    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getCategories();

    List<CategoryDto> getAllCategoriesAndCourses();

    boolean deleteCategoryById(Long id);

    boolean existsCategoryName(String name);

    List<CategoryDto> serchCategoryByName(String name);

    Long getCategoryCount();
}

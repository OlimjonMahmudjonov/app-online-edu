package uz.online_course.project.uz_online_course_project.service.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online_course.project.uz_online_course_project.dto.CategoryCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CategoryDto;
import uz.online_course.project.uz_online_course_project.entity.Category;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCreateCategoryDto(CategoryCreateDto categoryCreateDto) {
        if (existsCategoryName(categoryCreateDto.getName())) {
            throw new AlreadyExistsException("Category name already exists" + categoryCreateDto.getName());
        }
        Category category = new Category();
        category.setName(categoryCreateDto.getName());
        category.setDescription(categoryCreateDto.getDescription());
        Category saveCategory = categoryRepository.save(category);
        return converseTodo(saveCategory);
    }

    @Override
    public CategoryDto createUpdateCategoryDto(Long id, CategoryCreateDto categoryCreateDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found" + id));

        if (!category.getName().equals(categoryCreateDto.getName()) && existsCategoryName(categoryCreateDto.getName())) {
            throw new AlreadyExistsException("Category name already exists" + categoryCreateDto.getName());
        }

        category.setName(categoryCreateDto.getName());
        category.setDescription(categoryCreateDto.getDescription());

        Category updateCategory = categoryRepository.save(category);
        return converseTodo(updateCategory);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found" + id));
        return converseTodo(category);

    }

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll().stream().map(this::converseTodo).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getAllCategoriesAndCourses() {
        List<Category> categories = categoryRepository.findCategoriesWithCourses();
        return categories.stream()
                .map(this::converseTodo)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        // Kurslarni o‘chirib yuborish (orphanRemoval = true bo'lgani uchun yetarli)
        category.getCourses().clear();

        categoryRepository.delete(category);
    }




    @Override
    public boolean existsCategoryName(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public List<CategoryDto> serchCategoryByName(String name) {
        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
        return categories.stream().map(this::converseTodo).collect(Collectors.toList());
    }

    @Override
    public Long getCategoryCount() {
        return categoryRepository.count();
    }

    private CategoryDto converseTodo(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        if (category.getCourses() != null) {
            categoryDto.setCourseCount(category.getCourses().size());

        } else {
            categoryDto.setCourseCount(0);
        }
        return categoryDto;
    }
}

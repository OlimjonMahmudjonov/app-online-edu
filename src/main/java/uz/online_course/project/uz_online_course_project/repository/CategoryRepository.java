package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.online_course.project.uz_online_course_project.entity.Category;

import java.util.List;

public interface CategoryRepository  extends JpaRepository<Category,Long> {

    @Query("select  c  from  Category  c left  join  fetch  c.courses where  c.courses is not  empty ")
    List<Category> findCategoriesWithCourses();


    boolean existsByNameIgnoreCase(String name);

    List<Category> findByNameContainingIgnoreCase(String name);

}

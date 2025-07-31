package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.online_course.project.uz_online_course_project.entity.Blog;

public interface BlogRepository  extends JpaRepository<Blog, Long> {
    boolean existsById(Long id);

}

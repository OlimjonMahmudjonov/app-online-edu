package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.online_course.project.uz_online_course_project.entity.User;

public interface UserRepository  extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}

package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.online_course.project.uz_online_course_project.entity.User;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

  Optional< User> findByTelegramChatId(Long chatId);


    Optional<User> findByEmailAndVisibleTrue(String username);


    boolean existsByUsernameIgnoreCase(String username);
}

package uz.online_course.project.uz_online_course_project.service.user;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;
import uz.online_course.project.uz_online_course_project.dto.CreateUser;
import uz.online_course.project.uz_online_course_project.dto.UpdateUser;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
  //  private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id " + userId));
    }

    @Override
    public User createUser(CreateUser createUser) {
        if (userRepository.existsByEmail(createUser.getEmail())) {
            throw new AlreadyExistsException("Email Already Exists" + createUser.getEmail());
        }
        User user = new User();
        user.setEmail(createUser.getEmail());
        user.setPassword(createUser.getPassword());
        user.setUsername(createUser.getUsername());
        user.setRole(createUser.getRole());
        user.setVisible(createUser.getVisible());
        user.setTelegramUserName(createUser.getTelegramUserName());
        user.setTelegramChatId(createUser.getTelegramChatId());
        user.setCreatedDate(LocalDateTime.now());
        return userRepository.save(user);
    }


    @Override
    public User update(UpdateUser user, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setRole(user.getRole());
                    existingUser.setVisible(user.getVisible());
                    existingUser.setTelegramUserName(user.getTelegramUserName());
                    existingUser.setTelegramChatId(user.getTelegramChatId());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }


    @Override
    public void deleteUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete,
                () -> new ResourceNotFoundException("User not found with id " + userId));

    }
}

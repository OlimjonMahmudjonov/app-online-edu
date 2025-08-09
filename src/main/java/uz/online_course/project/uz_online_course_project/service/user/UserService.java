package uz.online_course.project.uz_online_course_project.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online_course.project.uz_online_course_project.dto.CreateUser;
import uz.online_course.project.uz_online_course_project.dto.UpdateUser;
import uz.online_course.project.uz_online_course_project.dto.UserDto;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class UserService implements IUserService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        return convertToUserDto(user);
    }

    @Override
    public UserDto createUser(CreateUser createUser) {
        if (existUserByName(createUser.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }

        if (existUserByEmail(createUser.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setUsername(createUser.getUsername());
        user.setEmail(createUser.getEmail());
       // user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setPassword(createUser.getPassword());
        user.setTelegramUserName(createUser.getTelegramUserName());
        user.setTelegramChatId(createUser.getTelegramChatId());
        user.setVisible(createUser.getVisible());

        User savedUser = userRepository.save(user);
        return convertToUserDto(savedUser);

    }

    @Override
    public UserDto update(UpdateUser userdto, Long userId) {
        User userById = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        if (!userById.getUsername().equals(userdto.getUsername()) && existUserByName(userdto.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }

        if (!userById.getEmail().equals(userdto.getEmail()) && existUserByEmail(userdto.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        userById.setId(userdto.getId());
        userById.setUsername(userdto.getUsername());
        userById.setEmail(userdto.getEmail());
       // userById.setPassword(passwordEncoder.encode(userdto.getPassword()));
        userById.setPassword(userdto.getPassword());
        userById.setRole(userdto.getRole());
        userById.setTelegramUserName(userdto.getTelegramUserName());
        userById.setCreatedDate(LocalDateTime.now());
        userById.setTelegramChatId(userdto.getTelegramChatId());

        User updatedUser = userRepository.save(userById);
        return convertToUserDto(updatedUser);


    }

    @Override
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

       userRepository.delete(user);


    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToUserDto).collect(Collectors.toList());
    }

    @Override
    public void resertEmail(Long userId, String email) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getEmail().equals(email)) {
            throw new AlreadyExistsException("Email already exists");
        }


        user.setEmail(email);
        userRepository.save(user);
    }


    @Override
    public boolean existUserByName(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean existUserByEmail(String email) {
        return userRepository.existsByEmail(email);

    }

    @Override
    public void resertPassword(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

       // user.setPassword(passwordEncoder.encode(password));
        user.setPassword(password);
        userRepository.save(user);
    }

    private UserDto convertToUserDto(User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setStatus(user.getStatus());
        userDto.setCreatedDate(user.getCreatedDate());
        userDto.setTelegramChatId(user.getTelegramChatId());
        userDto.setTelegramUserName(user.getTelegramUserName());
        userDto.setVisible(user.getVisible());


        return userDto;


    }


}

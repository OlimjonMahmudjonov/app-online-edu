package uz.online_course.project.uz_online_course_project.service.user;

import uz.online_course.project.uz_online_course_project.dto.UserDto;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.dto.CreateUser;
import uz.online_course.project.uz_online_course_project.dto.UpdateUser;

import java.util.List;

public interface IUserService {


    UserDto getUserById(Long userId);

    UserDto createUser(CreateUser createUser);

    UserDto update(UpdateUser user, Long userId);

    void deleteUser(Long userId);

    List<UserDto> getAllUsers();

    void resertEmail( Long userId,String email);

    void resertPassword( Long userId,String password);

    boolean existUserByName(String username);

    boolean existUserByEmail(String email);


}

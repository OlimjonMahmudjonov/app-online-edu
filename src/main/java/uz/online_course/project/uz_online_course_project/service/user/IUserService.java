package uz.online_course.project.uz_online_course_project.service.user;

import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.dto.CreateUser;
import uz.online_course.project.uz_online_course_project.dto.UpdateUser;

public interface IUserService {
    User getUserById(Long userId);

    User createUser(CreateUser createUser);

    User update(UpdateUser user, Long userId);

    void deleteUser(Long userId);

}

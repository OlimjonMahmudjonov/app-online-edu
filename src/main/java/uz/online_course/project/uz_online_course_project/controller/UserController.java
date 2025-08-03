package uz.online_course.project.uz_online_course_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.CreateUser;
import uz.online_course.project.uz_online_course_project.dto.UpdateUser;
import uz.online_course.project.uz_online_course_project.dto.UserDto;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.user.IUserService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PreAuthorize("hasAnyRole( 'INSTRUCTOR' ,'ADMIN')")
    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {

        try {
            UserDto userDto = userService.getUserById(userId);
            return ResponseEntity.ok(new ApiResponse("user retrieved successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Internal server error", null));
        }
    }
    @PreAuthorize("hasAnyRole()(,'STUDENT', 'INSTRUCTOR','ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CreateUser createUser) {

        try {
            UserDto userDto = userService.createUser(createUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("user created successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PreAuthorize("hasRole( 'ADMIN')")
    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId,@Valid @RequestBody UpdateUser updateUser) {

        try {
            UserDto userDto = userService.update(updateUser,userId);
            return ResponseEntity.ok(new ApiResponse("user updated successfully", userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Concurrent update conflict: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error", null));
        }

    }
    @PreAuthorize("hasRole( 'ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public  ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("user deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole(,'ADMIN')")
    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getUserAll (){
     List<UserDto > userDto =  userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse("users retrieved successfully", userDto));
    }





}

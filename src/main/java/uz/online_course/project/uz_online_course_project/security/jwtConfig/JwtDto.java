package uz.online_course.project.uz_online_course_project.security.jwtConfig;


import lombok.Getter;

@Getter
public class JwtDto {
    private String username;
    private String role;


    public JwtDto(String username, String role) {
        this.username = username;
        this.role = role;
    }


}

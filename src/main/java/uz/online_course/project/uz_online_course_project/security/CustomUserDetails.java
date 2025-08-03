package uz.online_course.project.uz_online_course_project.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.enums.GeneralRoles;
import uz.online_course.project.uz_online_course_project.enums.GeneralsStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails  implements UserDetails {

    private  String email ;
    private  String password ;
    private GeneralRoles role;
    private GeneralsStatus status;

    public CustomUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.status = user.getStatus();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       List<SimpleGrantedAuthority> roles = new ArrayList<>();
       roles.add(new SimpleGrantedAuthority(role.name()));
       return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(GeneralsStatus.ACTIVE);//
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

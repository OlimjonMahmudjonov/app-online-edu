package uz.online_course.project.uz_online_course_project.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;




@Getter
@Setter
public class CategoryCreateDto {
    @NotNull(message = "Name cannot be empty")
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @NotNull(message = "Description cannot be empty")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;
}

package uz.online_course.project.uz_online_course_project.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private  Long id ;
    private  String name ;
    private  String description;
    private  Integer  courseCount ;
}

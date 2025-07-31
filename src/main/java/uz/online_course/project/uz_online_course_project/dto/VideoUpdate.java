package uz.online_course.project.uz_online_course_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class VideoUpdate {


    @NotBlank(message = "title is not empty ")
    @Size(min = 45, max = 100)
    private String title;

    @NotBlank(message = "title is not empty ")
    @Size(min = 45, max = 100)
    private String originalFilename;

    @NotBlank(message = "title is not empty ")
    @Size(min = 45, max = 100)
    private String downloadUrl;


    @NotNull(message = "size is not negative ")
    private Long size;



}

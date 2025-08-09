package uz.online_course.project.uz_online_course_project.dto;

import jakarta.validation.constraints.Max;
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

public class VideoCreate {


    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Original filename is required")
    @Size(min = 3, max = 100, message = "Original filename must be between 3 and 100 characters")
    private String originalFilename;

    @NotBlank(message = "Download URL is required")
    @Size(min = 3, max = 100, message = "Download URL must be between 3 and 100 characters")
    private String downloadUrl;

    @NotNull(message = "Size is required")
    @Max(value = 2_000_000_000, message = "Video size cannot exceed 2GB")
    private Long size;

    @NotNull(message = "Lesson ID is required")
    private Long lessonId;

}

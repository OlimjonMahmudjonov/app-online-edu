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

public class VideoUpdate {

    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 100, message = "Original filename cannot exceed 100 characters")
    private String originalFilename;

    @Size(max = 100, message = "Download URL cannot exceed 100 characters")
    private String downloadUrl;

    @NotNull(message = "Size is required")
    @Max(value = 2_000_000_000, message = "Video size cannot exceed 2GB")
    private Long size;


}

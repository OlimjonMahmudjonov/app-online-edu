package uz.online_course.project.uz_online_course_project.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.online_course.project.uz_online_course_project.enums.GeneralLevel;


import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateDto {


    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @PositiveOrZero(message = "Original price must be positive or zero")
    private Double originalPrice;

    @PositiveOrZero(message = "Discount price must be positive or zero")
    private Double discountPrice;


    private LocalDateTime discountEndDate;

    @NotNull(message = "IsFree cannot be null")
    private Boolean isFree;

    @NotEmpty(message = "Duration cannot be empty")
    private String duration;// muddati

    @NotEmpty(message = "General Level  cannot be empty")
    private GeneralLevel generalLevel;

    @NotNull(message = "Category ID is required")
    private Long categoryId;


}

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
public class CourseCreateDto {


    @NotEmpty(message = "Title cannot be empty")
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Column(name = "description", nullable = false)
    private String description;

    @PositiveOrZero(message = "Original price must be positive or zero")
    @Column(name = "originalPrice")
    private Double originalPrice;

    @PositiveOrZero(message = "Discount price must be positive or zero")
    @Column(name = "discountPrice")
    private Double discountPrice;

    @Column(name = "discountEndDate")
    private LocalDateTime discountEndDate;

    @NotNull(message = "IsFree cannot be null")
    @Column(name = "isFree", nullable = false)
    private Boolean isFree;

    @NotEmpty(message = "Duration cannot be empty")
    @Column(name = "duration", nullable = false)
    private String duration;
    private  GeneralLevel generalLevel;

    @NotNull(message = "Category  ID is required")
    private  Long categoryId;

    @NotNull(message = "Instructor  ID is required")
    private Long instructorId;
}

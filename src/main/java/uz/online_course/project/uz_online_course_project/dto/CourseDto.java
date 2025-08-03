package uz.online_course.project.uz_online_course_project.dto;

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
public class CourseDto {
    private Long id;

    private String title;

    private String description;


    private Double originalPrice;

    private Double discountPrice;


    private LocalDateTime discountEndDate;

    private Boolean isFree;

    private String duration;

    private GeneralLevel generalLevel;
    private Long categoryId;
    private String categoryName;

    private Long instructorId;
    private String instructorName;

    private int lessonCount;
    private int reviewCount;
    private int paymentCount;
    private int commentCount;

    private Double currentPrice;
    private boolean hasActiveDiscount;

}

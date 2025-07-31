package uz.online_course.project.uz_online_course_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import uz.online_course.project.uz_online_course_project.enums.GeneralLevel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    @Column(name = "title")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Column(name = "description")
    private String description;

    @PositiveOrZero(message = "Original price must be positive or zero")
    @Column(name = "original_price")
    private Double originalPrice;

    @PositiveOrZero(message = "Discount price must be positive or zero")
    @Column(name = "discount_price")
    private Double discountPrice;

    @Column(name = "discount_end_date")
    private LocalDateTime discountEndDate;

    @NotNull(message = "IsFree cannot be null")
    @Column(name = "is_free")
    private Boolean isFree;

    @NotEmpty(message = "Duration cannot be empty")
    @Column(name = "duration")
    private String duration;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private GeneralLevel level;

    @NotNull(message = "Category cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull(message = "Instructor cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor")
    private User instructor;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<CourseComment> comments;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Payment> payments;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<UserRoles> userRoles;
}
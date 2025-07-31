package uz.online_course.project.uz_online_course_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @NotEmpty(message = "Video URL cannot be empty")
    @Column(name = "video_url")
    private String videoUrl;

    @NotNull(message = "Sequence cannot be null")
    @Column(name = "lesson_order")
    private Integer lessonOrder;

    @NotNull(message = "Course cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
    private List<Video> videos;
}
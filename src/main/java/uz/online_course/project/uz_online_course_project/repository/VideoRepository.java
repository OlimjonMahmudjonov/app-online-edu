package uz.online_course.project.uz_online_course_project.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.online_course.project.uz_online_course_project.dto.VideoDto;
import uz.online_course.project.uz_online_course_project.entity.Video;

import java.util.List;


public interface VideoRepository extends JpaRepository<Video,Long> {
    List<Video> findByLessonIdOrderByUploadDateDesc(Long lessonId);



    List<Video> findByTitleContainingIgnoreCase(String title);

    Long countByLessonId(Long id);



    boolean existsByTitleAndLessonId(@NotBlank(message = "title is not empty ") @Size(min = 45, max = 100) String title, @NotNull(message = "Lesson Id cannot be empty ") Long lessonId);

}

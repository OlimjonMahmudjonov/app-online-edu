package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.online_course.project.uz_online_course_project.entity.Video;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    boolean existsByDownloadUrlAndLessonId(String downloadUrl, Long lessonId);
    boolean existsByTitleAndLessonId(String title, Long lessonId);
    List<Video> findByLessonIdOrderByUploadDateDesc(Long lessonId);
    List<Video> findByTitleContainingIgnoreCase(String title);
    Long countByLessonId(Long id);
}
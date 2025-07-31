package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.online_course.project.uz_online_course_project.dto.VideoDto;
import uz.online_course.project.uz_online_course_project.entity.Video;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
    List<Video> findByLessonIdOrderByUploadDateDesc(Long lessonId);



    List<Video> findByTitleContainingIgnoreCase(String title);

    Long countByLessonId(Long id);

}

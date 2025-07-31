package uz.online_course.project.uz_online_course_project.service.video;

import org.springframework.stereotype.Repository;
import uz.online_course.project.uz_online_course_project.dto.VideoCreate;
import uz.online_course.project.uz_online_course_project.dto.VideoDto;
import uz.online_course.project.uz_online_course_project.dto.VideoUpdate;

import java.util.List;

@Repository
public interface IVideoService {
    VideoDto createVideo(VideoCreate videoCreate);

    VideoDto findVideoById(Long videoId);

    List<VideoDto> getAllVideos();


    VideoDto updateVideo(Long id, VideoUpdate videoUpdate);

    List<VideoDto> findAllVideosByLessonId(Long lessonId);

    void deleteVideoById(Long videoId);

    List<VideoDto> searchVideoByTitle(String title);

    Long getVideosCountByLessonId(Long id);

    Long getAllCountTotal();
}

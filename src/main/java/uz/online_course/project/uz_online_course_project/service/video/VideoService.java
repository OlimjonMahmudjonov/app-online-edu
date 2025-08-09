package uz.online_course.project.uz_online_course_project.service.video;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page; // Yangi import
import org.springframework.data.domain.Pageable; // To‘g‘ri import
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online_course.project.uz_online_course_project.dto.VideoCreate;
import uz.online_course.project.uz_online_course_project.dto.VideoDto;
import uz.online_course.project.uz_online_course_project.dto.VideoUpdate;
import uz.online_course.project.uz_online_course_project.entity.Lesson;
import uz.online_course.project.uz_online_course_project.entity.Video;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.LessonRepository;
import uz.online_course.project.uz_online_course_project.repository.VideoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoService implements IVideoService {

    private final LessonRepository lessonRepository;
    private final VideoRepository videoRepository;

    @Override
    public VideoDto createVideo(VideoCreate videoCreate) {
        Lesson lesson = lessonRepository.findById(videoCreate.getLessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson Not Found"));

        if (videoRepository.existsByDownloadUrlAndLessonId(videoCreate.getDownloadUrl(), videoCreate.getLessonId())) {
            throw new AlreadyExistsException("Video with this download URL already exists for the lesson");
        }

        if (videoRepository.existsByTitleAndLessonId(videoCreate.getTitle(), videoCreate.getLessonId())) {
            throw new AlreadyExistsException("Video Title or Lesson Id Already Exists");
        }

        Video video = new Video();
        video.setTitle(videoCreate.getTitle());
        video.setDownloadUrl(videoCreate.getDownloadUrl());
        video.setOriginalFilename(videoCreate.getOriginalFilename());
        video.setUploadDate(LocalDateTime.now());
        video.setSize(videoCreate.getSize());
        video.setLesson(lesson);

        Video createVideo = videoRepository.save(video);
        return convertToVideoDto(createVideo);
    }

    @Override
    public VideoDto findVideoById(Long videoId) {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFoundException("Video Not Found: " + videoId));
        return convertToVideoDto(video);
    }

    @Override
    public List<VideoDto> getAllVideos(Pageable pageable) {
        Page<Video> videoPage = videoRepository.findAll(pageable); // Pageable'dan foydalanish
        return videoPage.getContent().stream()
                .map(this::convertToVideoDto)
                .collect(Collectors.toList());
    }

    @Override
    public VideoDto updateVideo(Long id, VideoUpdate videoUpdate) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Video Not Found: " + id));

        video.setTitle(videoUpdate.getTitle());
        video.setDownloadUrl(videoUpdate.getDownloadUrl());
        video.setOriginalFilename(videoUpdate.getOriginalFilename());
        video.setSize(videoUpdate.getSize());
        Video updateVideo = videoRepository.save(video);
        return convertToVideoDto(updateVideo);
    }

    @Override
    public List<VideoDto> findAllVideosByLessonId(Long lessonId) {
        List<Video> videos = videoRepository.findByLessonIdOrderByUploadDateDesc(lessonId);
        return videos.stream().map(this::convertToVideoDto).collect(Collectors.toList());
    }

    @Override
    public void deleteVideoById(Long videoId) {
        videoRepository.findById(videoId).ifPresentOrElse(videoRepository::delete, () -> {
            throw new ResourceNotFoundException("Video Not Found: " + videoId);
        });
    }

    @Override
    public List<VideoDto> searchVideoByTitle(String title) {
        List<Video> videos = videoRepository.findByTitleContainingIgnoreCase(title);
        return videos.stream().map(this::convertToVideoDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long getVideosCountByLessonId(Long id) {
        return videoRepository.countByLessonId(id);
    }

    @Override
    public Long getAllCountTotal() {
        return videoRepository.count();
    }

    private VideoDto convertToVideoDto(Video video) {
        VideoDto videoDto = new VideoDto();
        videoDto.setId(video.getId());
        videoDto.setTitle(video.getTitle());
        videoDto.setSize(video.getSize());
        videoDto.setDownloadUrl(video.getDownloadUrl());
        videoDto.setOriginalTitle(video.getOriginalFilename());
        videoDto.setCreatedAt(video.getUploadDate());
        if (video.getLesson() != null) {
            videoDto.setLessonId(video.getLesson().getId());
            videoDto.setLessonTitle(video.getLesson().getTitle());
        }
        return videoDto;
    }
}
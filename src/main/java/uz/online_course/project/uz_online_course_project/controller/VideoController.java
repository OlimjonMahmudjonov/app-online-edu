package uz.online_course.project.uz_online_course_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.VideoCreate;
import uz.online_course.project.uz_online_course_project.dto.VideoDto;
import uz.online_course.project.uz_online_course_project.dto.VideoUpdate;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.video.IVideoService;

import java.util.List;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {
    private final IVideoService videoService;


    @PostMapping
    public ResponseEntity<ApiResponse> createVideo(@RequestBody VideoCreate videoCreate) {
        VideoDto videoDto = videoService.createVideo(videoCreate);
        return ResponseEntity.ok(new ApiResponse("Video created successfully", videoDto));
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> findVideoById(@PathVariable Long id) {
        VideoDto videoDto = videoService.findVideoById(id);
        return ResponseEntity.ok(new ApiResponse("Video found successfully", videoDto));
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllVideos() {
        List<VideoDto> videoDtos = videoService.getAllVideos();
        return ResponseEntity.ok(new ApiResponse("Videos found successfully", videoDtos));

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteVideoById(@PathVariable Long id) {
        videoService.deleteVideoById(id);
        return ResponseEntity.ok(new ApiResponse("Video deleted successfully", true));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateVideoResponse(@PathVariable Long id, @RequestBody VideoUpdate videoUpdate) {
        VideoDto videoDto = videoService.updateVideo(id, videoUpdate);
        return ResponseEntity.ok(new ApiResponse("Video updated successfully", videoDto));
    }

    @GetMapping("/search/title")
    public ResponseEntity<ApiResponse> searchVideoByTitle(@RequestParam String title) {
        List<VideoDto> videoDtos = videoService.searchVideoByTitle(title);
        return ResponseEntity.ok(new ApiResponse("Videos found successfully", videoDtos));
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<ApiResponse> getVideosCountByLessonId(@PathVariable Long id) {
        long count = videoService.getVideosCountByLessonId(id);
        return ResponseEntity.ok(new ApiResponse("Videos found successfully", count));
    }

    @GetMapping("/lesson/{id}")
    public ResponseEntity<ApiResponse> findAllVideosByLessonId(@PathVariable Long lessonId) {
        List<VideoDto> videoDtos = videoService.findAllVideosByLessonId(lessonId);
        return ResponseEntity.ok(new ApiResponse("Videos found successfully", videoDtos));
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> getAllCountTotal() {
        long count = videoService.getAllCountTotal();
        return ResponseEntity.ok(new ApiResponse("Videos found successfully", count));
    }


}

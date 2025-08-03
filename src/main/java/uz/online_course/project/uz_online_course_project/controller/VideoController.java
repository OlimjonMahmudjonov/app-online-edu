package uz.online_course.project.uz_online_course_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.VideoCreate;
import uz.online_course.project.uz_online_course_project.dto.VideoDto;
import uz.online_course.project.uz_online_course_project.dto.VideoUpdate;
import uz.online_course.project.uz_online_course_project.excaption.AlreadyExistsException;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.video.IVideoService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {
    private final IVideoService videoService;


    @PostMapping
    public ResponseEntity<ApiResponse> createVideo(@RequestBody VideoCreate videoCreate) {
        try {
            VideoDto videoDto = videoService.createVideo(videoCreate);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Video created successfully", videoDto));
        }  catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> findVideoById(@PathVariable Long id) {
        try {
            VideoDto videoDto = videoService.findVideoById(id);
            return ResponseEntity.ok(new ApiResponse("Video found successfully", videoDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Video not found", null));
        }
    }

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllVideos() {
        List<VideoDto> videoDtos = videoService.getAllVideos();
        return ResponseEntity.ok(new ApiResponse("Videos found successfully", videoDtos));

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteVideoById(@PathVariable Long id) {
        try {
            videoService.deleteVideoById(id);
            return ResponseEntity.ok(new ApiResponse("Video deleted successfully", null));
        } catch (ResourceNotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Video not found", null));
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateVideoResponse(@PathVariable Long id, @RequestBody VideoUpdate videoUpdate) {
        try {
            VideoDto videoDto = videoService.updateVideo(id, videoUpdate);
            return ResponseEntity.ok(new ApiResponse("Video updated successfully", videoDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Concurrent update conflict: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error", null));
        }
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

    @GetMapping("/lesson/{lessonId}")
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

package uz.online_course.project.uz_online_course_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.BlogDto;
import uz.online_course.project.uz_online_course_project.dto.BlogDtoCreate;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.blog.IBlogService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor

public class BlogController {
    private final IBlogService blogService;


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getBlogById(@PathVariable Long id) {
        try {
            BlogDto blogDto = blogService.getBlogById(id);
            return ResponseEntity.ok(new ApiResponse("Blog  get Id ", blogDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Blog not found ", id));
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBlogById(@PathVariable Long id) {
        try {
            blogService.deleteBlogById(id);
            return ResponseEntity.ok(new ApiResponse("Blog  delete Id ", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Internal server error", null));
        }
    }


    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllBlogs() {
        try {
            List<BlogDto> blogDtoList = blogService.getAllBlogs();
            return ResponseEntity.ok(new ApiResponse("Blog  get All Blogs ", blogDtoList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/exist/{id}")
    public ResponseEntity<ApiResponse> existsById(@PathVariable Long id) {
        boolean exists = blogService.existsById(id);
        return ResponseEntity.ok(new ApiResponse("Blog  exists Id ", exists));
    }


    @GetMapping("/author/{authorId}")
    public ResponseEntity<ApiResponse> getBlogsByAuthorId(@PathVariable Long authorId) {
        List<BlogDto> blogDtoList = blogService.getBlogsByAuthorId(authorId);
        return ResponseEntity.ok(new ApiResponse("Blog  get Blogs by Author Id ", blogDtoList));

    }


    @GetMapping("recents")
    public ResponseEntity<ApiResponse> getBlogRecentBlogs(@RequestParam Integer limit) {
        List<BlogDto> blogDtoList = blogService.getBlogRecentBlogs(limit);
        return ResponseEntity.ok(new ApiResponse("Blog  get Recent Blogs ", blogDtoList));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateBlogById(@PathVariable Long id, @Valid @RequestBody BlogDtoCreate blogDto) {
        try {
            BlogDto blogUpdate = blogService.updateBlogById(id, blogDto);
            return ResponseEntity.ok(new ApiResponse("Blog  update Id ", blogUpdate));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Concurrent update conflict: " + e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Internal server error", null));
        }
    }


    @PostMapping
    public ResponseEntity<ApiResponse> createBlog(@Valid @RequestBody BlogDtoCreate blogDtoCreate) {
        try {
            BlogDto blogDto = blogService.createBlog(blogDtoCreate);
            return ResponseEntity.ok(new ApiResponse("Blog  create Id ", blogDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


}

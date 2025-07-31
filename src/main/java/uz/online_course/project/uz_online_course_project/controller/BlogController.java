package uz.online_course.project.uz_online_course_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.BlogDto;
import uz.online_course.project.uz_online_course_project.dto.BlogDtoCreate;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.blog.IBlogService;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {
    private final IBlogService blogService;


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getBlogById(@PathVariable Long id) {
        BlogDto blogDto = blogService.getBlogById(id);
        return ResponseEntity.ok(new ApiResponse("Blog  get Id ", blogDto));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBlogById(@PathVariable Long id) {
        blogService.deleteBlogById(id);
        return ResponseEntity.ok(new ApiResponse("Blog  delete Id ", null));
    }


    @GetMapping("/get/all")
    public ResponseEntity<ApiResponse> getAllBlogs() {
        List<BlogDto> blogDtoList = blogService.getAllBlogs();
        return ResponseEntity.ok(new ApiResponse("Blog  get All Blogs ", blogDtoList));
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
    public ResponseEntity<ApiResponse> updateBlogById(@PathVariable Long id, @Valid  @RequestBody BlogDtoCreate blogDto) {
        BlogDto blogUpdate = blogService.updateBlogById(id, blogDto);
        return ResponseEntity.ok(new ApiResponse("Blog  update Id ", blogUpdate));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createBlog( @Valid @RequestBody BlogDtoCreate blogDtoCreate) {
        BlogDto blogDto = blogService.createBlog(blogDtoCreate);
        return ResponseEntity.ok(new ApiResponse("Blog  create Id ", blogDto));
    }


}

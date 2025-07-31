package uz.online_course.project.uz_online_course_project.service.blog;

import uz.online_course.project.uz_online_course_project.dto.BlogDto;
import uz.online_course.project.uz_online_course_project.dto.BlogDtoCreate;

import java.util.List;

public interface IBlogService {
    BlogDto getBlogById(Long id);

    void deleteBlogById(Long id);

    BlogDto updateBlogById(Long id, BlogDtoCreate blog);

    BlogDto createBlog(BlogDtoCreate blogDtoCreate);

    List<BlogDto> getAllBlogs();

    List<BlogDto> getBlogsByAuthorId(Long authorId);

    List<BlogDto> getBlogRecentBlogs(int limit);

    boolean existsById(Long id);

}

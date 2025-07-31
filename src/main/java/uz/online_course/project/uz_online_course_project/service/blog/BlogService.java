package uz.online_course.project.uz_online_course_project.service.blog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.online_course.project.uz_online_course_project.dto.BlogDto;
import uz.online_course.project.uz_online_course_project.dto.BlogDtoCreate;
import uz.online_course.project.uz_online_course_project.entity.Blog;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.BlogRepository;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BlogService implements IBlogService {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;


    @Override
    public BlogDto getBlogById(Long id) {
        return blogRepository.findById(id)
                .map(blog -> convertToDto(blog))
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));
    }

    @Override
    public void deleteBlogById(Long id) {
        blogRepository.findById(id).ifPresentOrElse(blogRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Blog not found");
                });

    }


    @Override
    public BlogDto updateBlogById(Long id, BlogDtoCreate blog) {

        return blogRepository.findById(id).map(existsBlog -> {
            existsBlog.setTitle(blog.getTitle());
            existsBlog.setContent(blog.getContent());
            if (blog.getAuthorId() != null) {
                User user = userRepository.findById(blog.getAuthorId()).orElseThrow(() ->
                        new ResourceNotFoundException("Author not found with " + blog.getAuthorId()));
                existsBlog.setAuthor(user);
            }
            return convertToDto(blogRepository.save(existsBlog));

        }).orElseThrow(() -> new ResourceNotFoundException("Blog not found with id " + id));
    }


    @Override
    public BlogDto createBlog(BlogDtoCreate blogDtoCreate) {
        User authUser = userRepository.findById(blogDtoCreate.getAuthorId()).orElseThrow(() ->
                new ResourceNotFoundException("Author not found with " + blogDtoCreate.getAuthorId()));
        return Optional.of(blogDtoCreate)
                .map(req -> {
                    Blog blog = new Blog();
                    blog.setTitle(req.getTitle());
                    blog.setContent(req.getContent());
                    blog.setCreatedAt(LocalDateTime.now());
                    blog.setAuthor(authUser);
                    return convertToDto(blogRepository.save(blog));
                }).orElseThrow(() -> new ResourceNotFoundException("Blog not found with id " + blogDtoCreate.getAuthorId()));
    }

    @Override
    public List<BlogDto> getAllBlogs() {
        return blogRepository.findAll()
                .stream()
                .map(blog -> convertToDto(blog))
                .collect(Collectors.toList());
    }

    @Override
    public List<BlogDto> getBlogsByAuthorId(Long authorId) {
        userRepository.findById(authorId).orElseThrow(() ->
                new ResourceNotFoundException("Author not found with " + authorId));
        return blogRepository.findAll().stream()
                .filter(blog -> blog.getAuthor().getId().equals(authorId))
                .map(blog -> convertToDto(blog))
                .collect(Collectors.toList());

    }

    @Override
    public List<BlogDto> getBlogRecentBlogs(int limit) {
        return blogRepository.findAll()
                .stream()
                .sorted((timeOne, timeTwo) -> timeTwo.getCreatedAt().compareTo(timeOne.getCreatedAt()))
                .limit(limit)
                .map(blog -> convertToDto(blog))
                .collect(Collectors.toList());

    }

    @Override
    public boolean existsById(Long id) {
        return blogRepository.existsById(id);
    }

    private BlogDto convertToDto(Blog blog) {

        BlogDto blogDto = new BlogDto();
        blogDto.setId(blog.getId());
        blogDto.setTitle(blog.getTitle());
        blogDto.setContent(blog.getContent());
        blogDto.setCreatedAt(blog.getCreatedAt());

        if (blog.getAuthor() != null) {
            blogDto.setAuthorId(blog.getAuthor().getId());
            blogDto.setAuthorName(blog.getAuthor().getUsername());
        }
        if (blog.getComments() != null) {
            blogDto.setCommentCount(blog.getComments().size());
        } else {
            blogDto.setCommentCount(0);
        }
        return blogDto;
    }

}

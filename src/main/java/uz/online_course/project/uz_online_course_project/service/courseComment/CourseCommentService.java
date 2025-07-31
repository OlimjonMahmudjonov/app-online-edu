package uz.online_course.project.uz_online_course_project.service.courseComment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.online_course.project.uz_online_course_project.dto.CourseCommentCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CourseCommentDto;
import uz.online_course.project.uz_online_course_project.entity.Blog;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.entity.CourseComment;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.BlogRepository;
import uz.online_course.project.uz_online_course_project.repository.CourseCommentRepository;
import uz.online_course.project.uz_online_course_project.repository.CourseRepository;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseCommentService implements ICourseComment {
    private final CourseCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BlogRepository blogRepository;
    private final ModelMapper modelMapper;

    @Override
    public CourseCommentDto getCourseCommentById(Long id) {
        return commentRepository.findById(id)
                .map(res -> convertToDto(res))
                .orElseThrow(() -> new ResourceNotFoundException("course comment not found"));
    }

    @Override
    public void deleteCourseCommentById(Long id) {
        commentRepository.findById(id)
                .ifPresentOrElse(commentRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("course comment not found");
                        });
    }

    @Override
    public CourseCommentDto updateCourseComment(Long id, CourseCommentDto commentDto) {
        return commentRepository.findById(id)
                .map(existsComment -> {

                    if (commentDto.getComment() != null && !commentDto.getComment().trim().isEmpty()) {
                        existsComment.setContent(commentDto.getComment());
                    }

                    if (commentDto.getUserId() != null) {
                        User user = userRepository.findById(commentDto.getUserId())
                                .orElseThrow(() -> new ResourceNotFoundException("user not found" + commentDto.getUserId()));
                        existsComment.setUser(user);
                    }

                    if (commentDto.getCourseId() != null) {
                        Course course = courseRepository.findById(commentDto.getCourseId())
                                .orElseThrow(() -> new ResourceNotFoundException("course not found" + commentDto.getCourseId()));
                        existsComment.setCourse(course);
                    }

                    if (commentDto.getBlogId() != null) {
                        Blog blog = blogRepository.findById(commentDto.getBlogId())
                                .orElseThrow(() -> new ResourceNotFoundException("blog not found" + commentDto.getBlogId()));
                        existsComment.setBlog(blog);
                    }
                    return convertToDto(commentRepository.save(existsComment));
                }).orElseThrow(() -> new ResourceNotFoundException("course comment not found"));
    }

    @Override
    public CourseCommentDto createCourseComment(CourseCommentCreateDto commentDto) {
        CourseCommentCreateDto dto = new CourseCommentCreateDto();
        dto.setCourseId(commentDto.getCourseId());
        dto.setUserId(commentDto.getUserId());
        dto.setBlogId(commentDto.getBlogId());
      //  dto.setContent(commentDto.getComment());

        return createComment(dto);
    }

    private CourseCommentDto createComment(CourseCommentCreateDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        if (dto.getCourseId() == null || dto.getBlogId() == null) {
            throw new IllegalArgumentException("Either courseId or blogId must be provided");
        }

        Course course = null;
        Blog blog = null;

        if (dto.getCourseId() != null) {
            course = courseRepository.findById(dto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("course not found"));
        }

        if (dto.getBlogId() != null) {
            blog = blogRepository.findById(dto.getBlogId())
                    .orElseThrow(() -> new ResourceNotFoundException("blog not found"));
        }

        final Course finalCourse = course;
        final Blog finalBlog = blog;
        final User finalUser = user;

        return Optional.of(dto)
                .map(commentDto -> {
                    CourseComment comment = new CourseComment();

                    comment.setContent(commentDto.getContent());
                    comment.setCreatedAt(LocalDateTime.now());
                    comment.setCourse(finalCourse);
                    comment.setUser(finalUser);
                    comment.setBlog(finalBlog);
                    return convertToDto(commentRepository.save(comment));

                }).orElseThrow(() -> new RuntimeException("Failed to create CourseComment"));
    }

    @Override
    public List<CourseCommentDto> getAllCourseComments() {
        return commentRepository.findAll().stream()
                .map(comment -> convertToDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseCommentDto> getCommentsByCourseId(Long courseId) {
        courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("course not found"));
        return commentRepository.findByCourseIdOrderByCreatedAtDesc(courseId)
                .stream().map(comment -> convertToDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseCommentDto> getCommentsByBlogId(Long blogId) {
        blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("blog not found"));
        return commentRepository.findByBlogIdOrderByCreatedAtDesc(blogId)
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseCommentDto> getCommentsByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        return commentRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseCommentDto> getRecentComments(Integer limit) {
        return commentRepository.findAll()
                .stream()
                .sorted((time_1, time_2) -> time_2.getCreatedAt().compareTo(time_1.getCreatedAt()))
                .limit(limit)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }

    @Override
    public Long getCommentsCountByCourseId(Long courseId) {
        courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("course not found"));
        return commentRepository.countByCourseId(courseId);
    }

    @Override
    public Long getCommentsCountByBlogId(Long blogId) {
        blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("blog not found"));
        // Tuzatilgan method nomi
        return commentRepository.countByBlogId(blogId);
    }

    private CourseCommentDto convertToDto(CourseComment comment) {
        CourseCommentDto dto = new CourseCommentDto();

        dto.setId(comment.getId());
        dto.setComment(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());

        // Entity fieldlarini tekshirish (DTO emas)
        if (comment.getCourse() != null) {
            dto.setCourseId(comment.getCourse().getId());
            dto.setCourseTitle(comment.getCourse().getTitle());
        }

        if (comment.getBlog() != null) {
            dto.setBlogId(comment.getBlog().getId());
            dto.setBlogTitle(comment.getBlog().getTitle());
        }

        if (comment.getUser() != null) {
            dto.setUserId(comment.getUser().getId());
            dto.setUsername(comment.getUser().getUsername());
        }

        return dto;
    }
}
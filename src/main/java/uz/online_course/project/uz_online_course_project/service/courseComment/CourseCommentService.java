package uz.online_course.project.uz_online_course_project.service.courseComment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional

public class CourseCommentService implements ICourseComment {

    private final CourseCommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final BlogRepository blogRepository;

    @Override
    public CourseCommentDto getCourseCommentById(Long id) {
        return commentRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Course comment not found with id: " + id));
    }

    @Override
    public void deleteCourseCommentById(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course comment not found with id: " + id);
        }
        commentRepository.deleteById(id);
    }

    @Override
    public CourseCommentDto updateCourseComment(Long id, CourseCommentDto commentDto) {

        CourseComment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course comment not found with id: " + id));

        if (commentDto.getComment() != null && !commentDto.getComment().trim().isEmpty()) {
            existingComment.setContent(commentDto.getComment());
        }

        if (commentDto.getUserId() != null) {
            User user = userRepository.findById(commentDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + commentDto.getUserId()));
            existingComment.setUser(user);
        }

        if (commentDto.getCourseId() != null) {
            Course course = courseRepository.findById(commentDto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + commentDto.getCourseId()));
            existingComment.setCourse(course);
        }

        if (commentDto.getBlogId() != null) {
            Blog blog = blogRepository.findById(commentDto.getBlogId())
                    .orElseThrow(() -> new ResourceNotFoundException("Blog not found with id: " + commentDto.getBlogId()));
            existingComment.setBlog(blog);
        }

        CourseComment savedComment = commentRepository.save(existingComment);
        return convertToDto(savedComment);
    }

    @Override
    public CourseCommentDto createCourseComment(CourseCommentCreateDto commentDto) {
        if (commentDto.getCourseId() == null ) {
            throw new IllegalArgumentException("Either courseId or blogId must be provided");
        }


        User user = userRepository.findById(commentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + commentDto.getUserId()));

        Course course = null;


        if (commentDto.getCourseId() != null) {
            course = courseRepository.findById(commentDto.getCourseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + commentDto.getCourseId()));
        }



        CourseComment comment = new CourseComment();
        comment.setContent(commentDto.getContent());
        comment.setUser(user);
        comment.setCourse(course);
        comment.setCreatedAt(LocalDateTime.now());

        CourseComment savedComment = commentRepository.save(comment);
        return convertToDto(savedComment);
    }

    @Override
    public List<CourseCommentDto> getAllCourseComments() {
        return commentRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseCommentDto> getCommentsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }

        return commentRepository.findByCourseIdOrderByCreatedAtDesc(courseId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseCommentDto> getCommentsByBlogId(Long blogId) {
        if (!blogRepository.existsById(blogId)) {
            throw new ResourceNotFoundException("Blog not found with id: " + blogId);
        }

        return commentRepository.findByBlogIdOrderByCreatedAtDesc(blogId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseCommentDto> getCommentsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        return commentRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseCommentDto> getRecentComments(Integer limit) {
        return commentRepository.findAll()
                .stream()
                .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
                .limit(limit != null && limit > 0 ? limit : 10)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return commentRepository.existsById(id);
    }

    @Override
    public Long getCommentsCountByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with id: " + courseId);
        }
        return commentRepository.countByCourseId(courseId);
    }

    @Override
    public Long getCommentsCountByBlogId(Long blogId) {
        if (!blogRepository.existsById(blogId)) {
            throw new ResourceNotFoundException("Blog not found with id: " + blogId);
        }
        return commentRepository.countByBlogId(blogId);
    }

    private CourseCommentDto convertToDto(CourseComment comment) {
        CourseCommentDto dto = new CourseCommentDto();

        dto.setId(comment.getId());
        dto.setComment(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());

        if (comment.getUser() != null) {
            dto.setUserId(comment.getUser().getId());
            dto.setUsername(comment.getUser().getUsername());
        }

        if (comment.getCourse() != null) {
            dto.setCourseId(comment.getCourse().getId());
            dto.setCourseTitle(comment.getCourse().getTitle());
        }

        if (comment.getBlog() != null) {
            dto.setBlogId(comment.getBlog().getId());
            dto.setBlogTitle(comment.getBlog().getTitle());
        }

        return dto;
    }
}
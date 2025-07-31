package uz.online_course.project.uz_online_course_project.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online_course.project.uz_online_course_project.dto.CourseCreateDto;
import uz.online_course.project.uz_online_course_project.dto.CourseDto;
import uz.online_course.project.uz_online_course_project.dto.CourseUpdateDto;
import uz.online_course.project.uz_online_course_project.entity.Category;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.enums.GeneralLevel;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.CategoryRepository;
import uz.online_course.project.uz_online_course_project.repository.CourseRepository;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public CourseDto createCourse(CourseCreateDto courseCreateDto) {
        User instruct = userRepository.findById(courseCreateDto.getInstructorId()).orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        Category category = categoryRepository.findById(courseCreateDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));


        Course course = new Course();

        course.setTitle(courseCreateDto.getTitle());
        course.setDescription(courseCreateDto.getDescription());
        course.setDiscountPrice(courseCreateDto.getDiscountPrice());
        course.setDiscountEndDate(courseCreateDto.getDiscountEndDate());
        course.setDuration(courseCreateDto.getDuration());
        course.setOriginalPrice(courseCreateDto.getOriginalPrice());
        course.setIsFree(courseCreateDto.getIsFree());
        course.setLevel(courseCreateDto.getGeneralLevel());
        course.setCategory(category);
        course.setInstructor(instruct);

        Course createdCourse = courseRepository.save(course);
        return converseToDto(createdCourse);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseUpdateDto courseUpdateDto) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if (courseUpdateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(courseUpdateDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found" + courseUpdateDto.getCategoryId()));
            course.setCategory(category);
        }

        course.setTitle(courseUpdateDto.getTitle());
        course.setDescription(courseUpdateDto.getDescription());
        course.setDiscountPrice(courseUpdateDto.getDiscountPrice());
        course.setDiscountEndDate(courseUpdateDto.getDiscountEndDate());
        course.setDuration(courseUpdateDto.getDuration());
        course.setOriginalPrice(courseUpdateDto.getOriginalPrice());
        course.setIsFree(courseUpdateDto.getIsFree());
        course.setLevel(courseUpdateDto.getGeneralLevel());

        Course updatedCourse = courseRepository.save(course);
        return converseToDto(updatedCourse);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        if ((course.getLessons() != null && !course.getLessons().isEmpty()) ||
                (course.getPayments() != null && !course.getPayments().isEmpty())) {
            throw new IllegalStateException("Course cannot be deleted because it has lessons or payments.");
        }

        courseRepository.deleteById(id);
        return true;
    }


    @Override
    public CourseDto getCourseById(Long codeId) {
        Course course = courseRepository.findById(codeId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return converseToDto(course);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(this::converseToDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getAllCoursesByCategoryId(Long categoryId) {
        List<Course> courses = courseRepository.findByCategoryId(categoryId);
        return courses.stream().map(this::converseToDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getAllCoursesByInstructorId(Long instructorId) {
        List<Course> courses = courseRepository.findByInstructorId(instructorId);
        return courses.stream().map(this::converseToDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getRecoursesIsFreeOrIsPayP(boolean isFreeOrIsPayP) {
        List<Course> courses = courseRepository.findByIsFree(isFreeOrIsPayP);
        return courses.stream().map(this::converseToDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getRecoursesByLevel(GeneralLevel generalLevel) {
        List<Course> courses = courseRepository.findByLevel(generalLevel);

        return courses.stream().map(this::converseToDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getRecoursesByTitle(String title) {
        List<Course> courses = courseRepository.findByTitleContainingIgnoreCase(title);
        return courses.stream().map(this::converseToDto).collect(Collectors.toList());
    }

    @Override
    public List<CourseDto> getCourseWithDiscount() {
        List<Course> courses = courseRepository.findCoursesWithActiveDiscount(LocalDateTime.now());
        return courses.stream().map(this::converseToDto).collect(Collectors.toList());
    }

    @Override
    public long getCoursesByInstructorId(Long instructorId) {
        return courseRepository.countByInstructorId(instructorId);
    }

    @Override
    public long getCoursesByCategoryId(Long categoryId) {
        return courseRepository.countByCategoryId(categoryId);
    }

    @Override
    public double getAverageRatingByCourseId(Long courseId) {
        Double avgRating = courseRepository.getAverageRatingByCourseId(courseId);
        return avgRating != null ? avgRating : 0.0;
    }

    @Override
    public long getTotalCoursesCount() {
        return courseRepository.count();
    }

    private CourseDto converseToDto(Course course) {
        CourseDto courseDto = new CourseDto();

        courseDto.setId(course.getId());
        courseDto.setTitle(course.getTitle());
        courseDto.setDescription(course.getDescription());
        courseDto.setOriginalPrice(course.getOriginalPrice());
        courseDto.setDiscountPrice(course.getDiscountPrice());
        courseDto.setDiscountEndDate(course.getDiscountEndDate());
        courseDto.setIsFree(course.getIsFree());
        courseDto.setDuration(course.getDuration());
        courseDto.setGeneralLevel(course.getLevel());

        if (course.getCategory() != null) {
            courseDto.setCategoryId(course.getCategory().getId());
            courseDto.setCategoryName(course.getCategory().getName());
        }

        if (course.getInstructor() != null) {
            courseDto.setInstructorId(course.getInstructor().getId());
            courseDto.setInstructorName(course.getInstructor().getUsername());
        }

        courseDto.setLessonCount(course.getLessons() != null ? course.getLessons().size() : 0);
        courseDto.setCommentCount(course.getComments() != null ? course.getComments().size() : 0);
        courseDto.setPaymentCount(course.getPayments() != null ? course.getPayments().size() : 0);
        courseDto.setReviewCount(course.getReviews() != null ? course.getReviews().size() : 0);

        courseDto.setCurrentPrice(getCurrentPrice(course));
        courseDto.setHasActiveDiscount(hasActiveDiscount(course));


        return courseDto;
    }

    private boolean hasActiveDiscount(Course course) {

        return course.getDiscountPrice() != null && course.getDiscountEndDate() != null
                && course.getDiscountEndDate().isAfter(LocalDateTime.now()) &&
                course.getDiscountPrice() < course.getOriginalPrice();
    }

    private Double getCurrentPrice(Course course) {
        if (course.getIsFree()) {
            return 0.0;
        }
        if (hasActiveDiscount(course)) {
            return course.getDiscountPrice();
        }
        return course.getOriginalPrice();
    }
}

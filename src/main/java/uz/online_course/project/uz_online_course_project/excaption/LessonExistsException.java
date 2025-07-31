package uz.online_course.project.uz_online_course_project.excaption;

public class LessonExistsException extends  RuntimeException {
    public LessonExistsException(String message) {
        super(message);
    }
}

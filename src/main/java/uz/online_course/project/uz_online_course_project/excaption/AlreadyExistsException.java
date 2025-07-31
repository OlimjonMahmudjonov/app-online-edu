package uz.online_course.project.uz_online_course_project.excaption;

public class AlreadyExistsException extends  RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

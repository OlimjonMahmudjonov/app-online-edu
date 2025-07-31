package uz.online_course.project.uz_online_course_project.request;


import lombok.Data;

@Data
public class AddLesson {
    private String title;
    private String content;
    private String videoUrl;
    private Integer order;
    private  Long courseId;
}

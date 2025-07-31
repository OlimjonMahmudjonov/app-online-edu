    package uz.online_course.project.uz_online_course_project.entity;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotEmpty;
    import lombok.*;
    import uz.online_course.project.uz_online_course_project.enums.GeneralRoles;

    import java.time.LocalDateTime;
    import java.util.List;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotEmpty(message = "Username cannot be empty")
        @Column(name = "username")
        private String username;

        @Email(message = "Email should be valid")
        @NotEmpty(message = "Email cannot be empty")
        @Column(name = "email")
        private String email;

        @NotEmpty(message = "Password cannot be empty")
        @Column(name = "password")
        private String password;

        @Column(name = "role")
        @Enumerated(EnumType.STRING)
        private GeneralRoles role;

        @Column(name = "visible")
        private Boolean visible = Boolean.TRUE;

        @Column(name = "created_date")
        private LocalDateTime createdDate;

        @Column(name = "telegram_chat_id")
        private String telegramChatId;

        @Column(name = "telegram_user_name")
        private String telegramUserName;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        private List<UserRoles> roles;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        private List<CourseComment> comments;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        private List<Review> reviews;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        private List<Payment> payments;

        @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
        private List<Blog> blogs;
    }
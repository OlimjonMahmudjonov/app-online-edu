package uz.online_course.project.uz_online_course_project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.*;
import uz.online_course.project.uz_online_course_project.enums.PayProgress;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Course cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    @Column(name = "amount")
    private BigDecimal amount;

    @PastOrPresent(message = "Payment date must be in the past or present")
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @NotNull(message = "Pay progress cannot be null")
    @Column(name = "pay_progress")
    @Enumerated(EnumType.STRING)
    private PayProgress payProgress;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "telegram_payment_token")
    private String telegramPaymentToken;
}
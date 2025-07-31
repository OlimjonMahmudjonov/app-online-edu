package uz.online_course.project.uz_online_course_project.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PaymentCreateDto {
    @NotNull(message = "User cannot be null")
    private Long userId;

    @NotNull(message = "Course cannot be null")
    private Long courseId;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;


    private String telegramPaymentToken;
}

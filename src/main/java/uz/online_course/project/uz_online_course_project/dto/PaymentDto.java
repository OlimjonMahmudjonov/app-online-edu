package uz.online_course.project.uz_online_course_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.online_course.project.uz_online_course_project.enums.PayProgress;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private  Long id ;
    private Long  userId ;
    private  String userName ;

    private  Long courseId ;
    private  String courseName ;

    private BigDecimal amount ;
    private LocalDateTime paymentDate;

    private PayProgress  payProgress;

    private  String transactionId ;
    private String telegramPaymentToken;


}

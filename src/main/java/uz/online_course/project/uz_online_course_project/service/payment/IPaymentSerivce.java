package uz.online_course.project.uz_online_course_project.service.payment;

import uz.online_course.project.uz_online_course_project.dto.PaymentCreateDto;
import uz.online_course.project.uz_online_course_project.dto.PaymentDto;
import uz.online_course.project.uz_online_course_project.enums.PayProgress;

import java.util.List;

public interface IPaymentSerivce {
    PaymentDto createPayment(PaymentCreateDto paymentCreateDto);

    PaymentDto updatePayment(Long payId, PayProgress payProgress);

    PaymentDto getPaymentById(Long paymentId);

    List<PaymentDto> getPaymentsByUserId(Long userId);

    List<PaymentDto> getPamentsByCourseId(Long courseId);

    List<PaymentDto> getPamentsByAll();

    boolean deletePaymentById(Long paymentId);

    PaymentDto processPayment(Long paymentId, String transactionId);

    boolean verifyPayment(String transactionId);
}

package uz.online_course.project.uz_online_course_project.service.payment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online_course.project.uz_online_course_project.dto.PaymentCreateDto;
import uz.online_course.project.uz_online_course_project.dto.PaymentDto;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.entity.Payment;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.enums.PayProgress;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.CourseRepository;
import uz.online_course.project.uz_online_course_project.repository.PaymentRepository;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentSerivce {

    private final UserRepository userRepository;
    private CourseRepository courseRepository;
    private PaymentRepository paymentRepository;

    @Override
    public PaymentDto createPayment(PaymentCreateDto paymentCreateDto) {
        User user = userRepository.findById(paymentCreateDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found" + paymentCreateDto.getUserId()));

        Course course = courseRepository.findById(paymentCreateDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found" + paymentCreateDto.getCourseId()));

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setCourse(course);
        payment.setAmount(paymentCreateDto.getAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPayProgress(PayProgress.PENDING);
        payment.setTelegramPaymentToken(paymentCreateDto.getTelegramPaymentToken());

        Payment savedPayment = paymentRepository.save(payment);
        return convertToDto(savedPayment);
    }


    @Override
    public PaymentDto updatePayment(Long payId, PayProgress payProgress) {
        Payment payment = paymentRepository.findById(payId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found" + payId));

        payment.setPayProgress(payProgress);
        Payment updatedPayment = paymentRepository.save(payment);
        return convertToDto(updatedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found" + paymentId));

        return convertToDto(payment);
    }

    @Override
    public List<PaymentDto> getPaymentsByUserId(Long userId) {
        List<Payment> payments = paymentRepository.findByUserId(userId);
        return payments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPamentsByCourseId(Long courseId) {
        List<Payment> payments = paymentRepository.findByCourseId(courseId);
        return payments.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPamentsByAll() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(pay -> convertToDto(pay)).collect(Collectors.toList());
    }

    @Override
    public boolean deletePaymentById(Long paymentId) {
        if (paymentRepository.findById(paymentId).isPresent()) {
            paymentRepository.deleteById(paymentId);
            return true;
        }
        return false;
    }

    @Override
    public PaymentDto processPayment(Long paymentId, String transactionId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found" + paymentId));

        payment.setTransactionId(transactionId);
        payment.setPayProgress(PayProgress.PROGRESSING);
        payment.setPaymentDate(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);
        return convertToDto(savedPayment);
    }

    @Override
    public boolean verifyPayment(String transactionId) {
        Optional<Payment> payment = paymentRepository.findByTransactionId(transactionId);
        return payment.isPresent() && payment.get().getPayProgress() == PayProgress.COMPLETED;
    }

    private PaymentDto convertToDto(Payment payment) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setId(payment.getId());
        paymentDto.setUserId(payment.getUser().getId());
        paymentDto.setUserName(payment.getUser().getUsername());
        paymentDto.setCourseId(payment.getCourse().getId());
        paymentDto.setCourseName(payment.getCourse().getTitle());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setPaymentDate(payment.getPaymentDate());
        paymentDto.setPayProgress(payment.getPayProgress());
        paymentDto.setTransactionId(payment.getTransactionId());
        paymentDto.setTelegramPaymentToken(payment.getTelegramPaymentToken());

        return paymentDto;

    }
}

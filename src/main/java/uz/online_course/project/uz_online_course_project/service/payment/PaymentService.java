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
@Transactional
public class PaymentService implements IPaymentSerivce {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDto createPayment(PaymentCreateDto paymentCreateDto) {
        User user = userRepository.findById(paymentCreateDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + paymentCreateDto.getUserId()));

        Course course = courseRepository.findById(paymentCreateDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + paymentCreateDto.getCourseId()));

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
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + payId));

        payment.setPayProgress(payProgress);

        if (payProgress == PayProgress.COMPLETED || payProgress == PayProgress.SUCCESS) {
            payment.setPaymentDate(LocalDateTime.now());
        }

        Payment updatedPayment = paymentRepository.save(payment);
        return convertToDto(updatedPayment);
    }

    @Override
    public PaymentDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        return convertToDto(payment);
    }

    @Override
    public List<PaymentDto> getPaymentsByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }

        List<Payment> payments = paymentRepository.findByUserId(userId);
        return payments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByCourseId(Long courseId) {
       /* if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Course not found with ID: " + courseId);
        }*/
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with ID: " + courseId));

        List<Payment> payments = paymentRepository.findByCourseId(courseId);
        return payments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getPaymentsByAll() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deletePaymentById(Long paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();

            if (payment.getPayProgress() == PayProgress.PENDING ||
                    payment.getPayProgress() == PayProgress.FAILED ||
                    payment.getPayProgress() == PayProgress.CANCELLED) {

                paymentRepository.deleteById(paymentId);
                return true;
            } else {
                throw new IllegalStateException("Cannot delete payment with status: " + payment.getPayProgress());
            }
        }
        return false;
    }

    @Override

    public PaymentDto processPayment(Long paymentId, String transactionId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + paymentId));

        if (payment.getPayProgress() != PayProgress.PENDING) {
            throw new IllegalStateException("Cannot process payment with status: " + payment.getPayProgress());
        }

        payment.setTransactionId(transactionId);
        payment.setPayProgress(PayProgress.PROGRESSING);

        Payment savedPayment = paymentRepository.save(payment);
        return convertToDto(savedPayment);
    }

    @Override
    public boolean verifyPayment(String transactionId) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            return false;
        }

        Optional<Payment> paymentOptional = paymentRepository.findByTransactionId(transactionId);
        return paymentOptional.isPresent() &&
                paymentOptional.get().getPayProgress() == PayProgress.COMPLETED;
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
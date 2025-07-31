package uz.online_course.project.uz_online_course_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.online_course.project.uz_online_course_project.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository  extends JpaRepository<Payment, Long> {
    List<Payment> findByUserId(Long userId);

    List<Payment> findByCourseId(Long courseId);

    Optional<Payment> findByTransactionId(String transactionId);

}

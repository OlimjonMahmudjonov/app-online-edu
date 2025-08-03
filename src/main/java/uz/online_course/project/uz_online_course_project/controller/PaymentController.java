package uz.online_course.project.uz_online_course_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.PaymentCreateDto;
import uz.online_course.project.uz_online_course_project.dto.PaymentDto;
import uz.online_course.project.uz_online_course_project.enums.PayProgress;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.payment.IPaymentSerivce;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor

public class PaymentController {

    private final IPaymentSerivce paymentService;

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR' ,'ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createPayment(@Valid @RequestBody PaymentCreateDto paymentCreateDto) {
        try {
            PaymentDto paymentDto = paymentService.createPayment(paymentCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Payment created successfully", paymentDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR' ,'ADMIN')")
    @PutMapping("/update/{paymentId}")
    public ResponseEntity<ApiResponse> updatePayment(
            @PathVariable Long paymentId,
            @RequestParam PayProgress payProgress) {
        try {
            PaymentDto paymentDto = paymentService.updatePayment(paymentId, payProgress);
            return ResponseEntity.ok(new ApiResponse("Payment updated successfully", paymentDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Payment not found", e));
        }
    }

    @GetMapping("/get/{paymentId}")
    public ResponseEntity<ApiResponse> getPaymentById(@PathVariable Long paymentId) {
        try {
            PaymentDto paymentDto = paymentService.getPaymentById(paymentId);
            return ResponseEntity.ok(new ApiResponse("Payment retrieved successfully", paymentDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Payment not found", e));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getPaymentsByUserId(@PathVariable Long userId) {
        try {
            List<PaymentDto> paymentDtos = paymentService.getPaymentsByUserId(userId);
            return ResponseEntity.ok(new ApiResponse("User payments retrieved successfully", paymentDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Payment not found", e));
        }
    }

    @PreAuthorize("hasAnyRole( 'INSTRUCTOR' ,'ADMIN')")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse> getPaymentsByCourseId(@PathVariable Long courseId) {
        try {
            List<PaymentDto> paymentDtos = paymentService.getPaymentsByCourseId(courseId);
            return ResponseEntity.ok(new ApiResponse("Course payments retrieved successfully", paymentDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Payment not found", e));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR' ,'ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPayments() {
        List<PaymentDto> paymentDtos = paymentService.getPaymentsByAll();
        return ResponseEntity.ok(new ApiResponse("All payments retrieved successfully", paymentDtos));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<ApiResponse> deletePaymentById(@PathVariable Long paymentId) {
        boolean isDeleted = paymentService.deletePaymentById(paymentId);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse("Payment deleted successfully", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Payment not found or cannot be deleted", false));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/process/{paymentId}")
    public ResponseEntity<ApiResponse> processPayment(
            @PathVariable Long paymentId,
            @RequestParam String transactionId) {
        PaymentDto paymentDto = paymentService.processPayment(paymentId, transactionId);
        return ResponseEntity.ok(new ApiResponse("Payment processed successfully", paymentDto));
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR' ,'ADMIN')")
    @GetMapping("/verify")
    public ResponseEntity<ApiResponse> verifyPayment(@RequestParam String transactionId) {
        boolean isVerified = paymentService.verifyPayment(transactionId);
        String message = isVerified ? "Payment verified successfully" : "Payment verification failed";
        return ResponseEntity.ok(new ApiResponse(message, isVerified));
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR' ,'ADMIN')")
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse> getPaymentsByStatus(@PathVariable PayProgress status) {
        List<PaymentDto> allPayments = paymentService.getPaymentsByAll();
        List<PaymentDto> filteredPayments = allPayments.stream()
                .filter(payment -> payment.getPayProgress() == status)
                .toList();
        return ResponseEntity.ok(new ApiResponse("Payments by status retrieved successfully", filteredPayments));
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR' ,'ADMIN')")
    @PatchMapping("/cancel/{paymentId}")
    public ResponseEntity<ApiResponse> cancelPayment(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentService.updatePayment(paymentId, PayProgress.CANCELLED);
        return ResponseEntity.ok(new ApiResponse("Payment cancelled successfully", paymentDto));
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR' ,'ADMIN')")
    @PatchMapping("/complete/{paymentId}")
    public ResponseEntity<ApiResponse> completePayment(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentService.updatePayment(paymentId, PayProgress.COMPLETED);
        return ResponseEntity.ok(new ApiResponse("Payment completed successfully", paymentDto));
    }

    @PreAuthorize("hasAnyRole('STUDENT', 'INSTRUCTOR' ,'ADMIN')")
    @PatchMapping("/fail/{paymentId}")
    public ResponseEntity<ApiResponse> failPayment(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentService.updatePayment(paymentId, PayProgress.FAILED);
        return ResponseEntity.ok(new ApiResponse("Payment marked as failed", paymentDto));
    }
}
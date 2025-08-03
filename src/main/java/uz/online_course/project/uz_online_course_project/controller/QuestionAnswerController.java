package uz.online_course.project.uz_online_course_project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.online_course.project.uz_online_course_project.dto.QuestionAnswerCreate;
import uz.online_course.project.uz_online_course_project.dto.QuestionAnswerDto;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.response.ApiResponse;
import uz.online_course.project.uz_online_course_project.service.questionanswer.IQuestionAnswerService;

import java.util.List;

@RestController
@RequestMapping("/api/question-answer")
@RequiredArgsConstructor

public class QuestionAnswerController {

    private final IQuestionAnswerService questionAnswerService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createQuestionAnswer(
            @Valid @RequestBody QuestionAnswerCreate questionAnswerCreate) {
        try {
            QuestionAnswerDto questionAnswerDto = questionAnswerService.createQuestionAnswer(questionAnswerCreate);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Question answer created successfully", questionAnswerDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateQuestionAnswer(
            @PathVariable Long id,
            @Valid @RequestBody QuestionAnswerCreate questionAnswerCreate) {
        try {
            QuestionAnswerDto questionAnswerDto = questionAnswerService.updateQuestionAnswer(id, questionAnswerCreate);
            return ResponseEntity.ok(new ApiResponse("Question answer updated successfully", questionAnswerDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getQuestionAnswerById(@PathVariable Long id) {
        QuestionAnswerDto questionAnswerDto = questionAnswerService.getQuestionAnswerById(id);
        return ResponseEntity.ok(new ApiResponse("Question answer retrieved successfully", questionAnswerDto));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllQuestionAnswer() {
        List<QuestionAnswerDto> questionAnswerList = questionAnswerService.getAllQuestionAnswer();
        return ResponseEntity.ok(new ApiResponse("All question answers retrieved successfully", questionAnswerList));
    }

    @GetMapping("/author/{authId}")
    public ResponseEntity<ApiResponse> getQuestionAnswersByAuthorId(@PathVariable Long authId) {
        try {
            List<QuestionAnswerDto> questionAnswerList = questionAnswerService.getQuestionAnswersByAnswerAuthId(authId);
            return ResponseEntity.ok(new ApiResponse("Question answers by author retrieved successfully", questionAnswerList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getQuestionAnswersByCategoryId(@PathVariable Long categoryId) {
        try {
            List<QuestionAnswerDto> questionAnswerList = questionAnswerService.getQuestionAnswersByCategoryId(categoryId);
            return ResponseEntity.ok(new ApiResponse("Question answers by category retrieved successfully", questionAnswerList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/unanswered")
    public ResponseEntity<ApiResponse> getUnansweredQuestions() {
        List<QuestionAnswerDto> unansweredQuestions = questionAnswerService.getUnansweredQuestions();
        return ResponseEntity.ok(new ApiResponse("Unanswered questions retrieved successfully", unansweredQuestions));
    }

    @GetMapping("/answered")
    public ResponseEntity<ApiResponse> getAnsweredQuestions() {
        List<QuestionAnswerDto> answeredQuestions = questionAnswerService.getAnsweredQuestions();
        return ResponseEntity.ok(new ApiResponse("Answered questions retrieved successfully", answeredQuestions));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse> getRecentQuestionsAnswers(
            @RequestParam(defaultValue = "10") Integer limit) {

        if (limit <= 0 || limit > 100) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse("Limit must be between 1 and 100", null));
        }

        List<QuestionAnswerDto> recentQuestions = questionAnswerService.getRecentQuestionsAnswers(limit);
        return ResponseEntity.ok(new ApiResponse("Recent question answers retrieved successfully", recentQuestions));
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<ApiResponse> existsById(@PathVariable Long id) {
        boolean exists = questionAnswerService.existsById(id);
        String message = exists ? "Question answer exists" : "Question answer does not exist";
        return ResponseEntity.ok(new ApiResponse(message, exists));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteQuestionAnswerById(@PathVariable Long id) {
        if (!questionAnswerService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Question answer not found with id: " + id, null));
        }

        questionAnswerService.deleteQuestionAnswerById(id);
        return ResponseEntity.ok(new ApiResponse("Question answer deleted successfully", null));
    }

}
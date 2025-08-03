package uz.online_course.project.uz_online_course_project.service.questionanswer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.online_course.project.uz_online_course_project.dto.QuestionAnswerCreate;
import uz.online_course.project.uz_online_course_project.dto.QuestionAnswerDto;
import uz.online_course.project.uz_online_course_project.entity.Category;
import uz.online_course.project.uz_online_course_project.entity.QuestionAnswer;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.CategoryRepository;
import uz.online_course.project.uz_online_course_project.repository.QuestionAnswerRepository;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional

public class QuestionAnswerService implements IQuestionAnswerService {
    private final UserRepository userRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public QuestionAnswerDto getQuestionAnswerById(Long id) {
        return questionAnswerRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionAnswer not found with id: " + id));
    }

    @Override
    public void deleteQuestionAnswerById(Long id) {
        QuestionAnswer questionAnswer = questionAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionAnswer not found with id: " + id));
        questionAnswerRepository.delete(questionAnswer);
    }

    @Override
    public QuestionAnswerDto updateQuestionAnswer(Long id, QuestionAnswerCreate questionAnswerCreate) {

        QuestionAnswer existingQuestionAnswer = questionAnswerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionAnswer not found with id: " + id));

        existingQuestionAnswer.setQuestion(questionAnswerCreate.getQuestion());
        existingQuestionAnswer.setAnswer(questionAnswerCreate.getAnswer());

        if (questionAnswerCreate.getAuthId() != null) {
            User user = userRepository.findById(questionAnswerCreate.getAuthId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + questionAnswerCreate.getAuthId()));
            existingQuestionAnswer.setAuthUser(user);
        }

        if (questionAnswerCreate.getCategoryId() != null) {
            Category category = categoryRepository.findById(questionAnswerCreate.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + questionAnswerCreate.getCategoryId()));
            existingQuestionAnswer.setCategory(category);
        }

        QuestionAnswer updatedQuestionAnswer = questionAnswerRepository.save(existingQuestionAnswer);
        return convertToDto(updatedQuestionAnswer);
    }

    @Override
    public QuestionAnswerDto createQuestionAnswer(QuestionAnswerCreate questionAnswerCreate) {

        User user = userRepository.findById(questionAnswerCreate.getAuthId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + questionAnswerCreate.getAuthId()));

        Category category = null;
        if (questionAnswerCreate.getCategoryId() != null) {
             category = categoryRepository.findById(questionAnswerCreate.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + questionAnswerCreate.getCategoryId()));
        }

        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setQuestion(questionAnswerCreate.getQuestion());
        questionAnswer.setAnswer(questionAnswerCreate.getAnswer());
        questionAnswer.setCreatedAt(LocalDateTime.now());
        questionAnswer.setAuthUser(user);
        questionAnswer.setCategory(category);

        QuestionAnswer savedQuestionAnswer = questionAnswerRepository.save(questionAnswer);
        return convertToDto(savedQuestionAnswer);
    }

    @Override
    public List<QuestionAnswerDto> getAllQuestionAnswer() {
        return questionAnswerRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionAnswerDto> getQuestionAnswersByAnswerAuthId(Long authId) {
        userRepository.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + authId));

        return questionAnswerRepository.findByAuthUserIdOrderByCreatedAtDesc(authId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionAnswerDto> getQuestionAnswersByCategoryId(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        return questionAnswerRepository.findByCategoryIdOrderByCreatedAtDesc(categoryId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionAnswerDto> getUnansweredQuestions() {
        return questionAnswerRepository.findUnansweredQuestions()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionAnswerDto> getAnsweredQuestions() {
        return questionAnswerRepository.findAnsweredQuestions()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionAnswerDto> getRecentQuestionsAnswers(int limit) {
        return questionAnswerRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .limit(limit)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return questionAnswerRepository.existsById(id);
    }

    private QuestionAnswerDto convertToDto(QuestionAnswer questionAnswer) {
        QuestionAnswerDto dto = new QuestionAnswerDto();
        dto.setId(questionAnswer.getId());
        dto.setQuestion(questionAnswer.getQuestion());
        dto.setAnswer(questionAnswer.getAnswer()); // String qiymat
        dto.setCreatedAt(questionAnswer.getCreatedAt());

        if (questionAnswer.getAuthUser() != null) {
            dto.setAuthId(questionAnswer.getAuthUser().getId());
            dto.setAuthName(questionAnswer.getAuthUser().getUsername());
        }

        if (questionAnswer.getCategory() != null) {
            dto.setCategoryId(questionAnswer.getCategory().getId());
            dto.setCategoryName(questionAnswer.getCategory().getName());
        }

        dto.setHasAnswer(questionAnswer.getAnswer() != null && !questionAnswer.getAnswer().trim().isEmpty());
        dto.setLikesCount(0);

        return dto;
    }
}
package uz.online_course.project.uz_online_course_project.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.online_course.project.uz_online_course_project.entity.Course;
import uz.online_course.project.uz_online_course_project.entity.Payment;
import uz.online_course.project.uz_online_course_project.entity.User;
import uz.online_course.project.uz_online_course_project.enums.GeneralRoles;
import uz.online_course.project.uz_online_course_project.enums.PayProgress;
import uz.online_course.project.uz_online_course_project.excaption.ResourceNotFoundException;
import uz.online_course.project.uz_online_course_project.repository.CourseRepository;
import uz.online_course.project.uz_online_course_project.repository.PaymentRepository;
import uz.online_course.project.uz_online_course_project.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class OnlineEdu extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "edu_online_paidbot";
    }

    @Override
    public String getBotToken() {
        return "8440611626:AAEczcVqTrKJTzVy1Xyl6B6UZYUN9ydQfMc";
    }
    /*
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CourseRepository courseRepository;

    private Map<Long, String> userStates = new HashMap<>();
    private Map<Long, Long> userCourseSelection = new HashMap<>();
    private static final String CART_NUMBER = "5614 6819 1401 6972";
    private static final Long ADMIN_CHAT_ID = 8081668815L; // @TheMaskGhost1 chat ID

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            String currentState = userStates.getOrDefault(chatId, TelegramState.STARTED.toString());

            User user = getOrCreateUser(chatId, update.getMessage().getFrom().getUserName());
            if (messageText.equals("/start")) {
                sendMessages(chatId, "Xush kelibsiz! Kurslarni ko‘rish uchun /buy ni bosing.");
                userStates.put(chatId, "START");
            } else if (messageText.equals("/buy")) {
                displayCourse(chatId);
                userStates.put(chatId, "SELECT_COURSE");
            } else if (currentState.equals("SELECT_COURSE")) {
                handleCourseSelection(chatId, messageText);
            } else if (messageText.equals("/confirm") && currentState.equals(TelegramState.SELECT_FREE.toString())) {
                confirmFreeCourse(chatId, user);
                userStates.put(chatId, "START");
            } else if (messageText.equals("/pay") && currentState.startsWith("PAYMENT_")) {
                handlePaymentInitialization(chatId);
            } else {
                sendMessages(chatId, "Noma‘lum komanda! /start ni bosing.");
            }
        } else if (update.hasCallbackQuery()) {
            Long chatId = update.getCallbackQuery().getFrom().getId().longValue();
            String callbackData = update.getCallbackQuery().getData();
            System.out.println("Callback qabul qilindi: " + callbackData);
            handleCourseSelection(chatId, callbackData);
        } else if (update.hasMessage() && update.getMessage().hasPhoto() && userStates.getOrDefault(update.getMessage().getChatId(), "").startsWith("PAYMENT_CONFIRM_")) {
            handlePhotoReceipt(update);
        }
    }

    private void handlePaymentInitialization(Long chatId) {

    }

    @Transactional
    protected User getOrCreateUser(Long chatId, String telegramUserName) {
        Optional<User> userOptional = userRepository.findByTelegramChatId(chatId);
        if (userOptional.isPresent()) {
            return userOptional.get(); // Mavjud foydalanuvchini qaytarish
        } else {
            User user = new User();
            user.setTelegramUserName(telegramUserName != null ? telegramUserName : "Anonymous");
            user.setTelegramChatId(chatId);
            user.setRole(GeneralRoles.STUDENT);
            user.setEmail("anonymous_" + chatId + "@example.com"); // Standart email
            user.setUsername(telegramUserName != null ? telegramUserName : "anonymous_" + chatId); // Standart username
            return userRepository.save(user); // Yangi foydalanuvchini saqlash
        }
    }

    private void handleCourseSelection(Long chatId, String messageText) {
        try {
            Long courseId = Long.parseLong(messageText);
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            if (courseOptional.isPresent()) {
                Course course = courseOptional.get();
                userCourseSelection.put(chatId, course.getId());
                if (course.getIsFree()) {
                    sendMessages(chatId, "Siz tekin kursni tanladingiz: " + course.getTitle() + ". Tasdiqlash uchun /confirm ni bosing.");
                    userStates.put(chatId, TelegramState.SELECT_FREE.toString());
                } else {
                    String price = course.getDiscountPrice() != null && course.getDiscountEndDate().isAfter(LocalDateTime.now())
                            ? "so‘m " + course.getDiscountPrice() : "so‘m " + course.getOriginalPrice();
                    sendMessages(chatId, "Siz tanlagan kurs narxi: " + price + ". To‘lovni amalga oshirish uchun /pay ni bosing.");
                    userStates.put(chatId, TelegramState.PAYMENT_.toString() + courseId);
                }
            } else {
                sendMessages(chatId, "Kurs topilmadi. Qayta urinib ko‘ring.");
            }
        } catch (NumberFormatException e) {
            sendMessages(chatId, "Noto‘g‘ri kurs ID. Iltimos, to‘g‘ri raqam kiriting.");
        }
    }

    private void displayCourse(Long chatId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        List<Course> courses = courseRepository.findAll();
        System.out.println("Kurslar soni: " + courses.size());
        if (courses.isEmpty()) {
            sendMessages(chatId, "Hozirda mavjud kurslar yo‘q. Keyinroq qayta urinib ko‘ring.");
            return;
        }

        StringBuilder listCourse = new StringBuilder("Kurslar ro‘yxati:\n");
        for (Course course : courses) {
            String price = course.getIsFree() ? "Bepul" :
                    (course.getDiscountPrice() != null && course.getDiscountEndDate().isAfter(LocalDateTime.now()))
                            ? "so‘m " + course.getDiscountPrice() : "so‘m " + course.getOriginalPrice();
            listCourse.append(course.getId()).append(" -> ").append(course.getTitle())
                    .append(" - ").append(price).append("\n");

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(String.valueOf(course.getId()));
            button.setCallbackData(String.valueOf(course.getId()));
            row.add(button);

            if (row.size() == 2) {
                rows.add(new ArrayList<>(row));
                row.clear();
            }
        }

        if (!row.isEmpty()) {
            rows.add(new ArrayList<>(row));
        }

        markup.setKeyboard(rows);
        System.out.println("Tugmalar qo‘shildi: " + rows.size());
        sendMessagesWithMarkup(chatId, listCourse.toString(), markup);
    }

    private void sendMessagesWithMarkup(Long chatId, String text, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(markup);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendMessages(chatId, "Xatolik yuz berdi: " + e.getMessage());
        }
    }

    private void confirmFreeCourse(Long chatId, User user) {
        Long courseId = userCourseSelection.get(chatId);
        if (courseId == null) {
            sendMessages(chatId, "Xatolik: Kurs tanlanmagan. /buy ni bosing.");
            return;
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            sendMessages(chatId, "Xatolik: Kurs topilmadi.");
            return;
        }
        if (course.getIsFree()) {
            sendMessages(chatId, "Tabriklaymiz! Siz kursga yozildingiz: " + course.getTitle());
            userStates.put(chatId, "START");
            userCourseSelection.remove(chatId);
        } else {
            sendMessages(chatId, "Xatolik: Bu kurs tekin emas. To‘lov uchun /pay ni bosing.");
        }
    }

    public void handlePaymentInitialization(Update update, String courseName, int amount, String token) {
        Long chatId = update.getMessage().getChatId();

        // 1. Kursni topish
        User course = courseRepository.findByName(courseName)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseName));

        // 2. Foydalanuvchini topish yoki yaratish
        User user = userRepository.findByTelegramChatId(chatId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setTelegramUserName("anon_" + chatId);
                    newUser.setTelegramChatId(chatId);
                    newUser.setRole(GeneralRoles.STUDENT);
                    newUser.setEmail("anon_" + chatId + "@test.com");
                    newUser.setUsername("anon_" + chatId);
                    return userRepository.save(newUser); // ❗ bazaga saqlanadi
                });

        // 3. Payment obyektini yaratish
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setCourse(course);
        payment.setAmount(BigDecimal.valueOf(amount));
        payment.setTelegramPaymentToken(token);
        payment.setPayProgress(PayProgress.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        // 4. Bazaga saqlash
        paymentRepository.save(payment);

        // 5. Javob yoki invoice yuborish (misol uchun)
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("To‘lov so‘rovi yuborildi: " + courseName + ", Narx: " + amount + " so‘m");
        try {
            execute(message); // Telegramga yuborish
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private void handlePhotoReceipt(Update update) {
        Long chatId = update.getMessage().getChatId();
        Long courseId = userCourseSelection.get(chatId);
        if (courseId == null) {
            sendMessages(chatId, "Xatolik: Kurs tanlanmagan. /buy ni bosing.");
            return;
        }
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) {
            sendMessages(chatId, "Xatolik: Kurs topilmadi.");
            return;
        }

        // Rasm fayl ID'sini olish
        String fileId = update.getMessage().getPhoto().get(update.getMessage().getPhoto().size() - 1).getFileId();
        String username = update.getMessage().getFrom().getUserName() != null ? update.getMessage().getFrom().getUserName() : "Anonymous";

        // Admin chat ID'ga rasm yuborish (@TheMaskGhost1, chat_id: 8081668815)
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(ADMIN_CHAT_ID.toString());
        sendPhoto.setPhoto(new InputFile(fileId)); // InputFile ishlatiladi
        sendPhoto.setCaption("Foydalanuvchi: " + username + "\n" +
                "Kurs: " + course.getTitle() + "\n" +
                "Narx: " + (course.getDiscountPrice() != null && course.getDiscountEndDate().isAfter(LocalDateTime.now())
                ? course.getDiscountPrice() : course.getOriginalPrice()) + " so‘m\n" +
                "To‘lov cheki qabul qilindi.");

        try {
            execute(sendPhoto);
            sendMessages(chatId, "To‘lov chekingiz qabul qilindi. Admin tasdiqlashini kuting.");

            // To‘lov holatini yangilash
            Payment payment = (Payment) paymentRepository.findByCourseIdAndUserTelegramChatIdAndPayProgress(courseId, chatId, PayProgress.PENDING)
                    .orElse(null);
            if (payment != null) {
                payment.setPayProgress(PayProgress.CHECK_SENT);
                payment.setPaymentDate(LocalDateTime.now());
                paymentRepository.save(payment);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendMessages(chatId, "Chekni yuborishda xatolik: " + e.getMessage());
        }
    }
    @Transactional
    protected void savePaymentInitiation(Course course, String token, Long chatId) {
        // 1. Foydalanuvchini olish yoki yaratish
        User user = userRepository.findByTelegramChatId(chatId)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setTelegramUserName("anonymous_" + chatId);
                    newUser.setTelegramChatId(chatId);
                    newUser.setRole(GeneralRoles.STUDENT);
                    newUser.setEmail("anonymous_" + chatId + "@example.com");
                    newUser.setUsername("anonymous_" + chatId);
                    return userRepository.save(newUser); // save qilinadi => attached entity
                });

        // 2. To'lovni yaratish
        Payment payment = new Payment();
        BigDecimal amount = BigDecimal.valueOf((course.getDiscountPrice() != null && course.getDiscountEndDate().isAfter(LocalDateTime.now()))
                ? course.getDiscountPrice() : course.getOriginalPrice());

        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPayProgress(PayProgress.PENDING);
        payment.setTelegramPaymentToken(token);
        payment.setCourse(course);
        payment.setUser(user); // endi attached user qo'yilyapti

        // 3. Saqlash
        paymentRepository.save(payment);
    }



    @SneakyThrows
    private void sendMessages(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(message);
        execute(sendMessage);
    }

   */
}
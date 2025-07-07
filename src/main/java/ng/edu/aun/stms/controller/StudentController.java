package ng.edu.aun.stms.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ng.edu.aun.stms.model.PrevQuizResult;
import ng.edu.aun.stms.model.Question;
import ng.edu.aun.stms.model.Quiz;
import ng.edu.aun.stms.model.QuizResult;
import ng.edu.aun.stms.model.Role;
import ng.edu.aun.stms.model.TutorSession;
import ng.edu.aun.stms.model.User;
import ng.edu.aun.stms.service.PrevQuizResultService;
import ng.edu.aun.stms.service.QuestionService;
import ng.edu.aun.stms.service.QuizResultService;
import ng.edu.aun.stms.service.QuizService;
import ng.edu.aun.stms.service.TutorSessionService;
import ng.edu.aun.stms.service.UserService;

@Controller
public class StudentController {
    @Autowired
    private TutorSessionService tutorSessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PrevQuizResultService prevQuizResultService;

    @GetMapping("/student/index")
    public String studentIndex(Model model) {
        User student = userService.getCurrentUser();
        model.addAttribute("joinedSessions", tutorSessionService.findByStudentsContaining(student));
        model.addAttribute("user", student);

        Set<Role> role = student.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }
        return "student-index";
    }

    @GetMapping("/student/sessions")
    public String showTutorSessions(Model model) {
        User student = userService.getCurrentUser();
        List<TutorSession> allSessions = tutorSessionService.findAll();
        List<TutorSession> availableSessions = allSessions.stream()
                .filter(session -> !session.getStudents().contains(student))
                .toList();
        model.addAttribute("tutorSessions", availableSessions);

        model.addAttribute("user", student);

        Set<Role> role = student.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }
        return "student-available-sessions";
    }

    @PostMapping("/student/join/{sessionId}")
    public String joinTutorSession(@PathVariable Long sessionId, Model model) {
        User student = userService.getCurrentUser();
        Optional<TutorSession> sessionOptional = tutorSessionService.findById(sessionId);

        if (sessionOptional.isPresent()) {
            TutorSession tutorSession = sessionOptional.get();

            List<TutorSession> existingSessions = tutorSessionService.findByStudentsContaining(student);

            for (TutorSession existingSession : existingSessions) {
                if (existingSession.getDay_of_week().equals(tutorSession.getDay_of_week())) {
                    if ((tutorSession.getStart_time().isBefore(existingSession.getEnd_time())
                            && tutorSession.getStart_time().isAfter(existingSession.getStart_time())) ||
                            (tutorSession.getEnd_time().isBefore(existingSession.getEnd_time())
                                    && tutorSession.getEnd_time().isAfter(existingSession.getStart_time()))
                            ||
                            (tutorSession.getStart_time().equals(existingSession.getStart_time())
                                    || tutorSession.getEnd_time().equals(existingSession.getEnd_time()))) {

                        model.addAttribute("error",
                                "A session already exists on " + tutorSession.getDay_of_week()
                                        + " with overlapping times.");
                        User currentUser = userService.getCurrentUser();
                        List<TutorSession> allSessions = tutorSessionService.findAll();
                        List<TutorSession> availableSessions = allSessions.stream()
                                .filter(session -> !session.getStudents().contains(currentUser))
                                .toList();
                        model.addAttribute("tutorSessions", availableSessions);

                        model.addAttribute("user", currentUser);

                        Set<Role> role = currentUser.getRoles();
                        for (Role userRole : role) {
                            model.addAttribute("role", userRole.getRoleName());
                        }
                        return "student-available-sessions";
                    }
                }
            }

            tutorSession.addStudent(student);
            tutorSessionService.save(tutorSession);
        }

        return "redirect:/student/sessions";
    }

    @GetMapping("/student/my-sessions")
    public String showJoinedSessions(Model model) {
        User student = userService.getCurrentUser();
        model.addAttribute("joinedSessions", tutorSessionService.findByStudentsContaining(student));
        return "student-joined-sessions";
    }

    @PostMapping("/student/leave/{sessionId}")
    public String leaveTutorSession(@PathVariable Long sessionId) {
        User student = userService.getCurrentUser();
        Optional<TutorSession> sessionOptional = tutorSessionService.findById(sessionId);

        if (sessionOptional.isPresent()) {
            TutorSession tutorSession = sessionOptional.get();
            tutorSession.getStudents().remove(student);
            tutorSessionService.save(tutorSession);
        }

        return "redirect:/student/index";
    }

    // QUIZ
    @GetMapping("/student/session/{sessionId}/quizzes")
    public String viewQuizes(@PathVariable("sessionId") Long sessionId, Model model) {
        List<Quiz> quizzes = quizService.findByTutorSessionId(sessionId);
        User student = userService.getCurrentUser();
        List<QuizResult> results = quizResultService.findByStudent(student);

        // Map quizId -> result
        Map<Long, QuizResult> quizResultsMap = results.stream()
                .collect(Collectors.toMap(
                        result -> result.getQuiz().getQuizId(),
                        result -> result,
                        (existing, replacement) -> existing // in case of duplicates
                ));

        model.addAttribute("quiz", quizzes);
        model.addAttribute("quizResultsMap", quizResultsMap);
        model.addAttribute("user", student);
        model.addAttribute("tutorSession", tutorSessionService.findById(sessionId).orElse(null));
        return "student-quizzes";
    }

    @GetMapping("/student/quiz/{quizId}/take")
    public String takeQuiz(@PathVariable Long quizId, Model model) {
        Optional<Quiz> quizOpt = quizService.findById(quizId);

        User student = userService.getCurrentUser();

        model.addAttribute("user", student);

        Set<Role> role = student.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        if (quizOpt.isPresent()) {
            model.addAttribute("quiz", quizOpt.get());
            model.addAttribute("questions", quizOpt.get().getQuestions());
            return "student-quiz-take";
        }
        return "redirect:/student/index";
    }

    @PostMapping("/student/quiz/submit")
    public String submitQuiz(@RequestParam Long quizId,
            @RequestParam Map<String, String> allParams,
            Model model) {
        Quiz quiz = quizService.findById(quizId).orElse(null);
        if (quiz == null) {
            model.addAttribute("error", "Quiz not found.");
            return "error";
        }

        List<Question> questions = questionService.findByQuizId(quizId);
        QuizResult result = new QuizResult();
        int totalQuestions = questions.size();
        int correctAnswers = result.getCorrectAnswers();
        Map<Long, String> answerMap = new HashMap<>();

        for (Question question : questions) {
            String key = "answers[" + question.getId() + "]";
            String studentAnswer = allParams.get(key);

            if (question.getType().equals("MULTIPLE_CHOICE")) {
                // Automatically check MULTIPLE_CHOICE answers
                if (studentAnswer != null && studentAnswer.equalsIgnoreCase(question.getCorrectAnswer())) {
                    result.setCorrectAnswers(correctAnswers += 1);
                }
                answerMap.put(question.getId(), studentAnswer); // Store the multiple choice answer
            } else if (question.getType().equals("TEXT")) {
                // For TEXT questions, store the student's input (even if it's empty)
                answerMap.put(question.getId(), studentAnswer != null ? studentAnswer : "PENDING");
            }
        }

        // Calculate score (e.g., percentage) based on multiple-choice questions
        double finalScore = ((double) result.getCorrectAnswers() / totalQuestions) * 100;

        // Save the result, but only for MULTIPLE_CHOICE questions for now

        result.setQuiz(quiz);
        result.setStudent(userService.getCurrentUser());
        result.setScore(finalScore);
        result.setAnswerMap(answerMap); // Save the answers map
        result.setSubmittedAt(LocalDateTime.now());
        quizResultService.save(result);

        model.addAttribute("score", finalScore);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("correctAnswers", correctAnswers);

        Long sessionId = quiz.getTutorSession().getId();
        return "redirect:/student/session/" + sessionId + "/quizzes";
    }

    @GetMapping("/student/quiz/{id}/result")
    public String seeResult(@PathVariable Long id, Model model) {
        User user = userService.getCurrentUser();
        List<QuizResult> quizResult = quizResultService.findByStudent(user);
        List<QuizResult> individualResult = new ArrayList<>();

        for (QuizResult quiz : quizResult) {
            if (quiz.getQuiz().getQuizId() == id) {
                individualResult.add(quiz);
            }
        }

        model.addAttribute("result", individualResult);
        return "student-see-result";
    }

    @GetMapping("/student/quiz/{id}/result/modal")
    public String getQuizResultModal(@PathVariable("id") Long quizId, Model model) {
        User student = userService.getCurrentUser();
        QuizResult result = quizResultService.findByStudentAndQuizId(student, quizId).orElse(null);
        model.addAttribute("result", result);
        return "fragments/student-result-modal :: resultContent";
    }

    @GetMapping("/student/quiz/retake/{quizResultId}/{quizId}")
    public String retakeQuiz(@PathVariable Long quizId, @PathVariable Long quizResultId, Model model) {
        QuizResult quizResult = quizResultService.findById(quizResultId);
        PrevQuizResult prevQuizResult = new PrevQuizResult();

        prevQuizResult.setPrev_score(quizResult.getScore());
        prevQuizResult.setPrev_submittedAt(quizResult.getSubmittedAt());
        prevQuizResult.setQuiz(quizResult.getQuiz());
        prevQuizResult.setUser(quizResult.getStudent());
        prevQuizResultService.save(prevQuizResult);

        quizResultService.delete(quizResultId);

        Long sessionId = quizResult.getQuiz().getTutorSession().getId();
        return "redirect:/student/session/" + sessionId + "/quizzes";
    }
}

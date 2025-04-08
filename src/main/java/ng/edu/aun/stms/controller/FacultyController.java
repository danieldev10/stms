package ng.edu.aun.stms.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ng.edu.aun.stms.model.Forum;
import ng.edu.aun.stms.model.ForumQuestion;
import ng.edu.aun.stms.model.ForumReply;
import ng.edu.aun.stms.model.Question;
import ng.edu.aun.stms.model.QuestionDTO;
import ng.edu.aun.stms.model.Quiz;
import ng.edu.aun.stms.model.QuizFormDTO;
import ng.edu.aun.stms.model.QuizResult;
import ng.edu.aun.stms.model.Role;
import ng.edu.aun.stms.model.TutorSession;
import ng.edu.aun.stms.model.User;
import ng.edu.aun.stms.repository.ForumQuestionRepository;
import ng.edu.aun.stms.service.ForumQuestionService;
import ng.edu.aun.stms.service.ForumReplyService;
import ng.edu.aun.stms.service.ForumService;
import ng.edu.aun.stms.service.QuestionService;
import ng.edu.aun.stms.service.QuizResultService;
import ng.edu.aun.stms.service.QuizService;
import ng.edu.aun.stms.service.TutorSessionService;
import ng.edu.aun.stms.service.UserService;

@Controller
public class FacultyController {
    @Autowired
    private TutorSessionService tutorSessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizResultService quizResultService;

    @Autowired
    private ForumService forumService;

    @Autowired
    private ForumQuestionService forumQuestionService;

    @Autowired
    private ForumQuestionRepository forumQuestionRepository;

    @Autowired
    private ForumReplyService forumReplyService;

    @GetMapping("/faculty/index")
    public String facultyIndex(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        Set<Role> role = user.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
        model.addAttribute("tutorSessions", tutorSessions);
        return "faculty-index";
    }

    @GetMapping("/faculty/course/create")
    public String createCourse(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        Set<Role> role = user.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
        model.addAttribute("tutorSessions", tutorSessions);
        return "faculty-create-course";
    }

    @PostMapping("/faculty/course/create")
    public String saveCourse(@ModelAttribute("tutorSession") TutorSession tutorSession,
            @RequestParam("day_of_week") String dayOfWeek, @RequestParam("start_time") String startTime,
            @RequestParam("end_time") String endTime, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startLocalTime = LocalTime.parse(startTime, timeFormatter);
        LocalTime endLocalTime = LocalTime.parse(endTime, timeFormatter);

        List<TutorSession> existingSessions = tutorSessionService.findByCreator(userService.getCurrentUser());

        for (TutorSession existingSession : existingSessions) {
            if ((startLocalTime.isBefore(existingSession.getEnd_time())
                    && startLocalTime.isAfter(existingSession.getStart_time())) ||
                    (endLocalTime.isBefore(existingSession.getEnd_time())
                            && endLocalTime.isAfter(existingSession.getStart_time()))
                    ||
                    (startLocalTime.equals(existingSession.getStart_time())
                            || endLocalTime.equals(existingSession.getEnd_time()))) {
                model.addAttribute("error", "A session already exists on " + dayOfWeek + " with overlapping times.");
                User user = userService.getCurrentUser();
                model.addAttribute("user", user);
                Set<Role> role = user.getRoles();
                for (Role userRole : role) {
                    model.addAttribute("role", userRole.getRoleName());
                }

                List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
                model.addAttribute("tutorSessions", tutorSessions);
                return "faculty-create-course";
            }
        }

        tutorSession.setDay_of_week(dayOfWeek);
        tutorSession.setStart_time(startLocalTime);
        tutorSession.setEnd_time(endLocalTime);

        User facultyUser = userService.findByUsername(userDetails.getUsername());
        tutorSession.setCreator(facultyUser);
        tutorSessionService.save(tutorSession);

        Forum forum = new Forum();
        forum.setTutorSession(tutorSession);
        forumService.save(forum);

        return "redirect:/faculty/index";
    }

    @GetMapping("/faculty/courses")
    public String showFacultyCourses(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User facultyUser = userService.findByUsername(userDetails.getUsername());
        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(facultyUser);
        model.addAttribute("tutorSessions", tutorSessions);
        return "faculty-courses";
    }

    @GetMapping("/faculty/course/edit/{id}")
    public String editCourse(@PathVariable("id") Long id, Model model) {
        TutorSession tutorSession = tutorSessionService.findById(id).orElse(null);
        model.addAttribute("tutorSession", tutorSession);

        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        Set<Role> role = user.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
        model.addAttribute("tutorSessions", tutorSessions);
        return "faculty-course-edit";
    }

    @PostMapping("/faculty/course/edit/{id}")
    public String saveEditedCourse(@ModelAttribute("course") TutorSession updatedTutorSession,
            @PathVariable("id") Long id) {
        TutorSession exisistingTutorSession = tutorSessionService.findById(id).orElse(null);
        if (exisistingTutorSession != null) {
            exisistingTutorSession.setCourse_name(updatedTutorSession.getCourse_name());
            exisistingTutorSession.setCourse_code(updatedTutorSession.getCourse_code());
            exisistingTutorSession.setDescription(updatedTutorSession.getDescription());
            exisistingTutorSession.setLocation(updatedTutorSession.getLocation());
            exisistingTutorSession.setMeeting_medium(updatedTutorSession.getMeeting_medium());
            exisistingTutorSession.setDay_of_week(updatedTutorSession.getDay_of_week());
            exisistingTutorSession.setStart_time(updatedTutorSession.getStart_time());
            exisistingTutorSession.setEnd_time(updatedTutorSession.getEnd_time());

            tutorSessionService.update(exisistingTutorSession);

            return "redirect:/faculty/index";
        } else {
            return "redirect:/faculty/index";
        }
    }

    @GetMapping("/faculty/course/delete/{id}")
    public String deleteTutorSession(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User facultyUser = userService.findByUsername(userDetails.getUsername());

        Optional<TutorSession> optionalTutorSession = tutorSessionService.findById(id);

        if (optionalTutorSession.isPresent()) {
            TutorSession tutorSession = optionalTutorSession.get();

            if (tutorSession.getCreator().getUser_id().equals(facultyUser.getUser_id())) {
                tutorSessionService.deleteById(id);
            }
        }

        return "redirect:/faculty/index";
    }

    @GetMapping("/faculty/course/manage/{id}")
    public String manageStudents(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User facultyUser = userService.findByUsername(userDetails.getUsername());
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        Optional<TutorSession> optionalTutorSession = tutorSessionService.findById(id);

        List<Quiz> quizes = quizService.findAll();
        List<Quiz> filteredQuizzes = quizes.stream()
                .filter(quiz -> quiz.getTutorSession().getId().equals(id))
                .collect(Collectors.toList());
        System.out.println(filteredQuizzes);
        model.addAttribute("quizzes", filteredQuizzes);

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
        model.addAttribute("tutorSessions", tutorSessions);

        Set<Role> role = user.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        if (optionalTutorSession.isPresent()) {
            TutorSession tutorSession = optionalTutorSession.get();

            if (tutorSession.getCreator().getUser_id().equals(facultyUser.getUser_id())) {
                model.addAttribute("tutorSession", tutorSession);
                model.addAttribute("students", tutorSession.getStudents());
                return "faculty-manage-students";
            }
        }

        return "redirect:/faculty/index";
    }

    @PostMapping("/faculty/course/manage/{sessionId}/remove/{studentId}")
    public String removeStudent(@PathVariable Long sessionId, @PathVariable Long studentId, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        User facultyUser = userService.findByUsername(userDetails.getUsername());

        Optional<TutorSession> optionalTutorSession = tutorSessionService.findById(sessionId);

        if (optionalTutorSession.isPresent()) {
            TutorSession tutorSession = optionalTutorSession.get();

            if (tutorSession.getCreator().getUser_id().equals(facultyUser.getUser_id())) {
                Optional<User> optionalStudent = userService.findById(studentId);

                if (optionalStudent.isPresent()) {
                    User student = optionalStudent.get();
                    tutorSession.getStudents().remove(student);
                    tutorSessionService.save(tutorSession);
                }
            }
        }

        return "redirect:/faculty/course/manage/" + sessionId;
    }

    // QUIZ
    @GetMapping("/faculty/course/{sessionId}/quiz/create")
    public String createQuiz(@PathVariable Long sessionId, Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        Set<Role> role = user.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
        model.addAttribute("tutorSessions", tutorSessions);

        Optional<TutorSession> optionalSession = tutorSessionService.findById(sessionId);
        if (optionalSession.isPresent()) {
            model.addAttribute("tutorSession", optionalSession.get());
            QuizFormDTO quizForm = new QuizFormDTO();
            quizForm.getQuestions().add(new QuestionDTO()); // one empty question initially
            model.addAttribute("quizForm", quizForm);
            return "faculty-create-quiz";
        }
        return "redirect:/faculty/courses";
    }

    @PostMapping("/faculty/course/{sessionId}/quiz/save")
    public String saveQuizWithQuestions(@PathVariable Long sessionId,
            @ModelAttribute QuizFormDTO quizForm) {
        Optional<TutorSession> sessionOpt = tutorSessionService.findById(sessionId);
        if (sessionOpt.isPresent()) {
            TutorSession session = sessionOpt.get();
            Quiz quiz = new Quiz();
            quiz.setTitle(quizForm.getTitle());
            quiz.setTutorSession(session);
            quizService.save(quiz);

            for (QuestionDTO qDto : quizForm.getQuestions()) {
                Question question = new Question();
                question.setQuiz(quiz);
                question.setText(qDto.getText());
                question.setType(qDto.getType());
                question.setCorrectAnswer(qDto.getCorrectAnswer());
                if ("MULTIPLE_CHOICE".equalsIgnoreCase(qDto.getType())) {
                    question.setOptions(Arrays.asList(qDto.getOptions().split(",")));
                }
                questionService.save(question);
            }
        }
        return "redirect:/faculty/course/manage/" + sessionId;
    }

    @GetMapping("/faculty/course/{id}/quizes")
    public String viewAllQuizes(@PathVariable Long id, Model model) {
        List<Quiz> quizes = quizService.findAll();
        List<Quiz> filteredQuizzes = quizes.stream()
                .filter(quiz -> quiz.getTutorSession().getId().equals(id))
                .collect(Collectors.toList());
        System.out.println(filteredQuizzes);
        model.addAttribute("quizzes", filteredQuizzes);
        return "faculty-quizes";
    }

    @GetMapping("/faculty/quiz/{quizId}/edit")
    public String editQuiz(@PathVariable Long quizId, Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        Set<Role> role = user.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
        model.addAttribute("tutorSessions", tutorSessions);

        Quiz quiz = quizService.findById(quizId).orElse(null);
        if (quiz != null) {
            model.addAttribute("quiz", quiz);
            model.addAttribute("questions", quiz.getQuestions()); // Add the questions to the model
            return "faculty-edit-quiz"; // The form to edit both quiz and questions
        }
        return "redirect:/faculty/index";
    }

    @PostMapping("/faculty/quiz/{quizId}/edit")
    public String saveEditedQuiz(@ModelAttribute("quiz") Quiz updatedQuiz, @PathVariable("quizId") Long quizId) {
        Quiz quiz = quizService.findById(quizId).orElse(null);
        if (quiz != null) {
            quiz.setTitle(updatedQuiz.getTitle());

            // Save quiz details
            quizService.update(quiz);

            // Convert Set<Question> to List<Question>
            List<Question> questionList = new ArrayList<>(updatedQuiz.getQuestions());

            // Now handle the questions
            for (int i = 0; i < questionList.size(); i++) {
                Question updatedQuestion = questionList.get(i);
                updatedQuestion.setQuiz(quiz);
                questionService.update(updatedQuestion); // Assuming you have an update method for questions
            }

            Long sessionId = quiz.getTutorSession().getId();
            return "redirect:/faculty/course/manage/" + sessionId; // Redirect after saving
        }
        return "redirect:/faculty/index";
    }

    @GetMapping("/faculty/quiz/{quizId}/delete")
    public String deleteQuiz(@PathVariable("quizId") Long quizId) {
        Quiz quiz = quizService.findById(quizId).orElse(null);
        Long sessionId = quiz.getTutorSession().getId();

        quizService.deleteById(quizId);
        return "redirect:/faculty/course/manage/" + sessionId;
    }

    // QUESTION
    @GetMapping("/faculty/quiz/{quizId}/questions")
    public String viewQuestions(@PathVariable Long quizId, Model model) {
        Optional<Quiz> optionalQuiz = quizService.findById(quizId);
        if (optionalQuiz.isPresent()) {
            model.addAttribute("quiz", optionalQuiz.get());
            model.addAttribute("questions", questionService.findByQuizId(quizId));
            return "faculty-view-questions";
        }
        return "redirect:/faculty/index";
    }

    @GetMapping("/faculty/quiz/{quizId}/questions/add")
    public String addQuestion(@PathVariable Long quizId, Model model) {
        Optional<Quiz> optionalQuiz = quizService.findById(quizId);

        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        Set<Role> role = user.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
        model.addAttribute("tutorSessions", tutorSessions);

        if (optionalQuiz.isPresent()) {
            model.addAttribute("quiz", optionalQuiz.get());
            model.addAttribute("question", new Question());
            return "faculty-add-question";
        }
        return "redirect:/faculty/index";
    }

    @PostMapping("/faculty/quiz/{quizId}/questions/save")
    public String saveQuestion(@PathVariable Long quizId, @ModelAttribute("question") Question question,
            @RequestParam("options") String options) {
        Optional<Quiz> optionalQuiz = quizService.findById(quizId);
        if (optionalQuiz.isPresent()) {
            question.setQuiz(optionalQuiz.get());
            if ("MULTIPLE_CHOICE".equals(question.getType())) {
                question.setOptions(Arrays.asList(options.split(",")));
            }
            questionService.save(question);
        }
        return "redirect:/faculty/quiz/" + quizId + "/questions";
    }

    @GetMapping("/faculty/quiz/question/{questionId}/edit")
    public String editQuestion(@PathVariable Long questionId, Model model) {
        Question question = questionService.findById(questionId).orElse(null);
        model.addAttribute("question", question);

        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        Set<Role> role = user.getRoles();
        for (Role userRole : role) {
            model.addAttribute("role", userRole.getRoleName());
        }

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(user);
        model.addAttribute("tutorSessions", tutorSessions);
        return "faculty-edit-question";
    }

    @PostMapping("/faculty/quiz/question/{questionId}/edit")
    public String saveEditedQuestion(@ModelAttribute("question") Question updatedQuestion,
            @PathVariable("questionId") Long questionId) {
        Question question = questionService.findById(questionId).orElse(null);
        if (question != null) {
            question.setText(updatedQuestion.getText());
            question.setType(updatedQuestion.getType());
            if (updatedQuestion.getType() == "TEXT") {
                question.setOptions(null);
            } else {
                question.setOptions(updatedQuestion.getOptions());
            }
            question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
            questionService.update(question);

            Long quizId = question.getQuiz().getQuizId();

            return "redirect:/faculty/quiz/" + quizId + "/edit";
        } else {
            return "redirect:/faculty/index";
        }
    }

    @GetMapping("/faculty/quiz/question/{questionId}/delete")
    public String deleteQuestion(@PathVariable("questionId") Long questionId) {
        // Retrieve the question to find its associated quiz
        Question question = questionService.findById(questionId).orElse(null);
        if (question != null) {
            Long quizId = question.getQuiz().getQuizId(); // Get the quiz ID associated with the question
            questionService.deleteById(questionId); // Delete the question
            return "redirect:/faculty/quiz/" + quizId + "/edit"; // Redirect to the edit quiz page
        }
        return "redirect:/faculty/index"; // Redirect if the question wasn't found
    }

    @GetMapping("/faculty/quiz/{quizId}/review")
    public String reviewQuiz(@PathVariable Long quizId, Model model) {
        Quiz quiz = quizService.findById(quizId).orElse(null);
        if (quiz == null) {
            model.addAttribute("error", "Quiz not found.");
            return "error";
        }

        List<QuizResult> quizResults = quizResultService.findByQuiz(quiz);

        model.addAttribute("quizResults", quizResults);

        return "faculty-quiz-review";
    }

    @PostMapping("/faculty/quiz/{quizId}/review")
    public String updateReview(@PathVariable Long quizId, @RequestParam Map<Long, String> reviewedAnswers,
            Model model) {
        Quiz quiz = quizService.findById(quizId).orElse(null);
        if (quiz == null) {
            model.addAttribute("error", "Quiz not found.");
            return "error";
        }

        List<QuizResult> quizResults = quizResultService.findByQuiz(quiz);

        // Review each student's answer for TEXT questions
        for (QuizResult result : quizResults) {
            Map<Long, String> answers = result.getAnswerMap();
            int correctAnswers = result.getCorrectAnswers();

            System.out.println(correctAnswers);

            for (Map.Entry<Long, String> entry : answers.entrySet()) {
                Long questionId = entry.getKey();
                String answer = entry.getValue();
                Question question = questionService.findById(questionId).orElse(null);

                if (question != null) {
                    // If it's a TEXT question, update based on faculty's review
                    if (question.getType().equals("TEXT")) {
                        // Retrieve the reviewed answer (either CORRECT or INCORRECT) from faculty
                        String reviewedAnswer = reviewedAnswers.get(questionId);
                        answers.put(questionId, reviewedAnswer); // Update the answer

                        // If the faculty marks the answer as correct, increment correctAnswers count
                        if ("CORRECT".equals(reviewedAnswer)) {
                            correctAnswers++;
                        }
                    }
                    // For MULTIPLE_CHOICE questions, the answer is automatically checked
                    else if (question.getType().equals("MULTIPLE_CHOICE")) {
                        // Correctness is determined by comparing the answer to the correct option
                        if (answer.equalsIgnoreCase(question.getCorrectAnswer())) {
                            correctAnswers++;
                        }
                    }
                }
            }

            // After reviewing all questions, calculate the final score
            double finalScore = calculateScore(quiz, correctAnswers);

            // Save the updated quiz result with the final score
            result.setCorrectAnswers(correctAnswers);
            result.setScore(finalScore);
            quizResultService.save(result);
            System.out.println(correctAnswers);
        }

        model.addAttribute("message", "Quiz results have been updated.");
        return "redirect:/faculty/courses";
    }

    @GetMapping("/faculty/course/{courseId}/forum/create")
    public String createForum(Model model) {
        return "faculty-create-forum";
    }

    @GetMapping("/forum/{tutorSessionId}")
    public String enterForum(@PathVariable("tutorSessionId") Long id, Model model) {
        TutorSession tutorSession = tutorSessionService.findById(id).orElse(null);
        model.addAttribute("tutorSession", tutorSession);

        Forum forum = forumService.findByTutorSession(tutorSession)
                .orElseGet(() -> {
                    Forum newForum = new Forum(tutorSession);
                    forumService.save(newForum);
                    return newForum;
                });

        List<ForumQuestion> questions = forumQuestionRepository.findByForumId(forum.getId());

        for (ForumQuestion question : questions) {
            System.out.println("Question: " + question.getTitle() +
                    " by " + question.getAuthor().getUsername());
        }
        model.addAttribute("forum", forum);
        model.addAttribute("questions", questions);
        model.addAttribute("newQuestion", new ForumQuestion());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Logged in user: " + authentication.getName());

        User currentUser = userService.getCurrentUser();
        Set<Role> roles = currentUser.getRoles();
        // Check if the user has the 'FACULTY' or 'STUDENT' role and add it to the model
        model.addAttribute("userRole", roles.stream().anyMatch(role -> role.getRoleName().equals("FACULTY")) ? "FACULTY"
                : roles.stream().anyMatch(role -> role.getRoleName().equals("STUDENT")) ? "STUDENT" : "UNKNOWN");

        model.addAttribute("newReply", new ForumReply());
        model.addAttribute("user", userService.getCurrentUser());

        for (Role userRole : roles) {
            model.addAttribute("role", userRole.getRoleName());
        }

        List<TutorSession> tutorSessions = tutorSessionService.findByCreator(currentUser);
        model.addAttribute("tutorSessions", tutorSessions);
        return "forum";
    }

    private double calculateScore(Quiz quiz, int correctAnswers) {
        int totalQuestions = quiz.getQuestions().size();
        return (double) correctAnswers / totalQuestions * 100;
    }

    @PostMapping("/forum/{tutorSessionId}/post")
    public String saveForumQuestion(@PathVariable("tutorSessionId") Long id, Model model,
            @ModelAttribute ForumQuestion newQuestion) {
        TutorSession session = tutorSessionService.findById(id).orElse(null);
        Forum forum = forumService.findByTutorSession(session).orElseThrow();

        newQuestion.setForum(forum);
        newQuestion.setCreatedAt(LocalDateTime.now());
        newQuestion.setAuthor(userService.getCurrentUser()); // assumes you're logged in

        forumQuestionService.save(newQuestion);

        return "redirect:/forum/" + id;
    }

    @PostMapping("/forum/{tutorSessionId}/question/{questionId}/reply")
    public String replyToQuestion(@PathVariable Long tutorSessionId,
            @PathVariable Long questionId,
            @ModelAttribute("reply") ForumReply reply,
            Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ForumQuestion question = forumQuestionService.findById(questionId);

        reply.setAuthor(user);
        reply.setQuestion(question);
        forumReplyService.save(reply);

        return "redirect:/forum/" + tutorSessionId;
    }

}

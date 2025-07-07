package ng.edu.aun.stms.service;

import ng.edu.aun.stms.model.Quiz;
import ng.edu.aun.stms.model.QuizResult;
import ng.edu.aun.stms.model.User;
import java.util.List;
import java.util.Optional;

public interface QuizResultService {
    void save(QuizResult quizResult);

    List<QuizResult> findByStudent(User student);

    void delete(Long id);

    QuizResult findById(Long id);

    List<QuizResult> findByQuiz(Quiz quiz);

    Optional<QuizResult> findByStudentAndQuizId(User student, Long quizId);

}

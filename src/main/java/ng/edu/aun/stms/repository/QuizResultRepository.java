package ng.edu.aun.stms.repository;

import ng.edu.aun.stms.model.Quiz;
import ng.edu.aun.stms.model.QuizResult;
import ng.edu.aun.stms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByStudent(User student);

    List<QuizResult> findByQuiz(Quiz quiz);

    Optional<QuizResult> findByStudentAndQuiz_QuizId(User student, Long quizId);

}

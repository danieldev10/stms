package ng.edu.aun.stms.service.implemetation;

import ng.edu.aun.stms.model.Quiz;
import ng.edu.aun.stms.model.QuizResult;
import ng.edu.aun.stms.model.User;
import ng.edu.aun.stms.repository.QuizResultRepository;
import ng.edu.aun.stms.service.QuizResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizResultServiceImpl implements QuizResultService {

    @Autowired
    private QuizResultRepository quizResultRepository;

    public void save(QuizResult quizResult) {
        quizResultRepository.save(quizResult);
    }

    public List<QuizResult> findByStudent(User student) {
        return quizResultRepository.findByStudent(student);
    }

    @Override
    public void delete(Long id) {
        quizResultRepository.deleteById(id);
    }

    @Override
    public QuizResult findById(Long id) {
        return quizResultRepository.findById(id).orElse(null);
    }

    @Override
    public List<QuizResult> findByQuiz(Quiz quiz) {
        return quizResultRepository.findByQuiz(quiz);
    }

    @Override
    public Optional<QuizResult> findByStudentAndQuizId(User student, Long quizId) {
        return quizResultRepository.findByStudentAndQuiz_QuizId(student, quizId);
    }
}

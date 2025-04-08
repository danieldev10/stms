package ng.edu.aun.stms.service.implemetation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.stms.model.Question;
import ng.edu.aun.stms.repository.QuestionRepository;
import ng.edu.aun.stms.service.QuestionService;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void save(Question question) {
        questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<Question> findByQuizId(Long quizId) {
        return questionRepository.findByQuiz_QuizId(quizId);
    }

    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    public void deleteById(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public void update(Question question) {
        questionRepository.save(question);
    }
}

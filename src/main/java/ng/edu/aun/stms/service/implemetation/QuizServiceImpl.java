package ng.edu.aun.stms.service.implemetation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.stms.model.Quiz;
import ng.edu.aun.stms.repository.QuizRepository;
import ng.edu.aun.stms.service.QuizService;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public void save(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> findAll() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> findById(Long id) {
        return quizRepository.findById(id);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }

    @Override
    public void update(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> findByTutorSessionId(Long id) {
        return quizRepository.findByTutorSession_Id(id);
    }

}

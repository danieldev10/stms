package ng.edu.aun.stms.service;

import java.util.List;
import java.util.Optional;

import ng.edu.aun.stms.model.Quiz;

public interface QuizService {
    public void save(Quiz quiz);

    public List<Quiz> findAll();

    public Optional<Quiz> findById(Long id);

    public void update(Quiz quiz);

    public void deleteById(Long id);

    public List<Quiz> findByTutorSessionId(Long id);
}

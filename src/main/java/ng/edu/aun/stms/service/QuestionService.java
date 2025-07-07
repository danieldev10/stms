package ng.edu.aun.stms.service;

import java.util.List;
import java.util.Optional;

import ng.edu.aun.stms.model.Question;

public interface QuestionService {
    public void save(Question question);

    public List<Question> findAll();

    public List<Question> findByQuizId(Long quizId);

    public Optional<Question> findById(Long id);

    public void update(Question question);

    public void deleteById(Long id);
}

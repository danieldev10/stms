package ng.edu.aun.stms.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User student;

    @ManyToOne
    private Quiz quiz;

    private Double score;

    private LocalDateTime submittedAt;

    private int correctAnswers;

    @ElementCollection
    private Map<Long, String> answerMap = new HashMap<>();

    public QuizResult() {
    }

    public QuizResult(User student, Quiz quiz, Double score, LocalDateTime submittedAt, Map<Long, String> answerMap,
            int correctAnswers) {
        this.student = student;
        this.quiz = quiz;
        this.score = score;
        this.submittedAt = submittedAt;
        this.answerMap = answerMap;
        this.correctAnswers = correctAnswers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Map<Long, String> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<Long, String> answerMap) {
        this.answerMap = answerMap;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    @Override
    public String toString() {
        return "QuizResult [id=" + id + ", score=" + score + ", correctAnswers=" + correctAnswers + "]";
    }

}

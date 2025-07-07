package ng.edu.aun.stms.model;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class PrevQuizResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private User user;

    private Double prev_score;

    private LocalDateTime prev_submittedAt;

    public PrevQuizResult() {
    }

    public PrevQuizResult(Quiz quiz, Double prev_score, LocalDateTime prev_submittedAt, User user) {
        this.quiz = quiz;
        this.prev_score = prev_score;
        this.prev_submittedAt = prev_submittedAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Double getPrev_score() {
        return prev_score;
    }

    public void setPrev_score(Double prev_score) {
        this.prev_score = prev_score;
    }

    public LocalDateTime getPrev_submittedAt() {
        return prev_submittedAt;
    }

    public void setPrev_submittedAt(LocalDateTime prev_submittedAt) {
        this.prev_submittedAt = prev_submittedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

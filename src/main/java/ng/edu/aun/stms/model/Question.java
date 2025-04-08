package ng.edu.aun.stms.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String type; // "MULTIPLE_CHOICE" or "TEXT"

    @ElementCollection
    private List<String> options; // Stores multiple-choice options

    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    public Question() {
    }

    public Question(String text, String type, List<String> options, String correctAnswer, Quiz quiz) {
        this.text = text;
        this.type = type;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

}

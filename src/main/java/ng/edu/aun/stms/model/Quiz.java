package ng.edu.aun.stms.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "tutor_session_id", nullable = false)
    private TutorSession tutorSession;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Question> questions = new HashSet<>();

    public Quiz() {
    }

    public Quiz(String title, TutorSession tutorSession) {
        this.title = title;
        this.tutorSession = tutorSession;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long id) {
        this.quizId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TutorSession getTutorSession() {
        return tutorSession;
    }

    public void setTutorSession(TutorSession tutorSession) {
        this.tutorSession = tutorSession;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}

package ng.edu.aun.stms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "tutor_session_id", nullable = false)
    private TutorSession tutorSession;

    public Forum() {
    }

    public Forum(TutorSession tutorSession) {
        this.tutorSession = tutorSession;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TutorSession getTutorSession() {
        return tutorSession;
    }

    public void setTutorSession(TutorSession tutorSession) {
        this.tutorSession = tutorSession;
    }

}

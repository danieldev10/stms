package ng.edu.aun.stms.service;

import java.util.List;
import java.util.Optional;

import ng.edu.aun.stms.model.TutorSession;
import ng.edu.aun.stms.model.User;

public interface TutorSessionService {
    void save(TutorSession tutorSession);

    void update(TutorSession tutorSession);

    List<TutorSession> findAll();

    Optional<TutorSession> findById(Long id);

    List<TutorSession> findByCreator(User creator);

    void deleteById(Long id);

    List<TutorSession> findByStudentsContaining(User student);
}

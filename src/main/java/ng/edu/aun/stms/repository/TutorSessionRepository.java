package ng.edu.aun.stms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ng.edu.aun.stms.model.TutorSession;
import ng.edu.aun.stms.model.User;

@Repository
public interface TutorSessionRepository extends JpaRepository<TutorSession, Long> {
    List<TutorSession> findByCreator(User creator);

    List<TutorSession> findByStudentsContaining(User student);
}

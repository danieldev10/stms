package ng.edu.aun.stms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ng.edu.aun.stms.model.Forum;
import ng.edu.aun.stms.model.TutorSession;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

    Optional<Forum> findByTutorSession(TutorSession tutorSession);

}

package ng.edu.aun.stms.service;

import java.util.Optional;

import ng.edu.aun.stms.model.Forum;
import ng.edu.aun.stms.model.TutorSession;

public interface ForumService {
    public void save(Forum forum);

    Optional<Forum> findByTutorSession(TutorSession tutorSession);

}

package ng.edu.aun.stms.service.implemetation;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.stms.model.Forum;
import ng.edu.aun.stms.model.TutorSession;
import ng.edu.aun.stms.repository.ForumRepository;
import ng.edu.aun.stms.service.ForumService;

@Service
@Transactional
public class ForumServiceImpl implements ForumService {
    @Autowired
    private ForumRepository forumRepository;

    @Override
    public void save(Forum forum) {
        forumRepository.save(forum);
    }

    @Override
    public Optional<Forum> findByTutorSession(TutorSession tutorSession) {
        return forumRepository.findByTutorSession(tutorSession);
    }

}

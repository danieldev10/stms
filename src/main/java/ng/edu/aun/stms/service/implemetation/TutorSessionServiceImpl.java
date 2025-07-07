package ng.edu.aun.stms.service.implemetation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.stms.model.TutorSession;
import ng.edu.aun.stms.model.User;
import ng.edu.aun.stms.repository.TutorSessionRepository;
import ng.edu.aun.stms.service.TutorSessionService;

@Service
@Transactional
public class TutorSessionServiceImpl implements TutorSessionService {
    @Autowired
    private TutorSessionRepository tutorSessionRepository;

    @Override
    public void save(TutorSession tutorSession) {
        tutorSessionRepository.save(tutorSession);
    }

    @Override
    public List<TutorSession> findAll() {
        return tutorSessionRepository.findAll();
    }

    @Override
    public Optional<TutorSession> findById(Long id) {
        return tutorSessionRepository.findById(id);
    }

    @Override
    public List<TutorSession> findByCreator(User creator) {
        return tutorSessionRepository.findByCreator(creator);
    }

    @Override
    public void deleteById(Long id) {
        tutorSessionRepository.deleteById(id);
    }

    @Override
    public List<TutorSession> findByStudentsContaining(User student) {
        return tutorSessionRepository.findByStudentsContaining(student);
    }

    @Override
    public void update(TutorSession tutorSession) {
        tutorSessionRepository.save(tutorSession);
    }
}

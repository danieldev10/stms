package ng.edu.aun.stms.service.implemetation;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.stms.model.ForumQuestion;
import ng.edu.aun.stms.repository.ForumQuestionRepository;
import ng.edu.aun.stms.service.ForumQuestionService;

@Service
@Transactional
public class ForumQuestionServiceImpl implements ForumQuestionService {
    @Autowired
    private ForumQuestionRepository forumQuestionRepository;

    @Override
    public List<ForumQuestion> findAll() {
        return forumQuestionRepository.findAll();
    }

    @Override
    public void save(ForumQuestion forumQuestion) {
        forumQuestionRepository.save(forumQuestion);
    }

    @Override
    public List<ForumQuestion> findByForumId(Long forumId) {
        return forumQuestionRepository.findByForumId(forumId);
    }

    @Override
    public ForumQuestion findById(Long id) {
        return forumQuestionRepository.findById(id).orElse(null);
    }

}

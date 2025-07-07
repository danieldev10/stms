package ng.edu.aun.stms.service.implemetation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.stms.model.ForumReply;
import ng.edu.aun.stms.repository.ForumReplyRepository;
import ng.edu.aun.stms.service.ForumReplyService;

@Service
@Transactional
public class ForumReplyServiceImpl implements ForumReplyService {
    @Autowired
    private ForumReplyRepository forumReplyRepository;

    @Override
    public void save(ForumReply forumReply) {
        forumReplyRepository.save(forumReply);
    }

    @Override
    public ForumReply findById(Long id) {
        return forumReplyRepository.findById(id).orElse(null);
    }

}

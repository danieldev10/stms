package ng.edu.aun.stms.service;

import ng.edu.aun.stms.model.ForumReply;

public interface ForumReplyService {
    void save(ForumReply forumReply);

    ForumReply findById(Long id);
}

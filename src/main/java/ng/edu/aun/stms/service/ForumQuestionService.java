package ng.edu.aun.stms.service;

import java.util.List;

import ng.edu.aun.stms.model.ForumQuestion;

public interface ForumQuestionService {
    List<ForumQuestion> findAll();

    void save(ForumQuestion forumQuestion);

    List<ForumQuestion> findByForumId(Long forumId);

    ForumQuestion findById(Long id);
}

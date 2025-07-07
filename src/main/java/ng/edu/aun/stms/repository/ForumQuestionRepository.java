package ng.edu.aun.stms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ng.edu.aun.stms.model.ForumQuestion;

@Repository
public interface ForumQuestionRepository extends JpaRepository<ForumQuestion, Long> {
    List<ForumQuestion> findByForumId(Long forumId);
}

package ng.edu.aun.stms.repository;

import ng.edu.aun.stms.model.Quiz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByTutorSession_Id(Long id);
}

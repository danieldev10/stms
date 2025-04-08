package ng.edu.aun.stms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ng.edu.aun.stms.model.PrevQuizResult;

@Repository
public interface PrevQuizResultRepository extends JpaRepository<PrevQuizResult, Long> {

}

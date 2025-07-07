package ng.edu.aun.stms.service.implemetation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.stms.model.PrevQuizResult;
import ng.edu.aun.stms.repository.PrevQuizResultRepository;
import ng.edu.aun.stms.service.PrevQuizResultService;

@Service
@Transactional
public class PrevQuizResultServiceImpl implements PrevQuizResultService {
    @Autowired
    private PrevQuizResultRepository prevQuizResultRepository;

    @Override
    public void save(PrevQuizResult prevQuizResult) {
        prevQuizResultRepository.save(prevQuizResult);
    }

}

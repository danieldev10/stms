package ng.edu.aun.stms.model;

import java.util.ArrayList;
import java.util.List;

public class QuizFormDTO {
    private String title;
    private List<QuestionDTO> questions = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

}

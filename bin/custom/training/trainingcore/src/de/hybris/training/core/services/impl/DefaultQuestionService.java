package de.hybris.training.core.services.impl;

import de.hybris.training.core.daos.QuestionDao;
import com.myapp.questions.model.QuestionModel;
import de.hybris.training.core.services.QuestionService;
import org.springframework.beans.factory.annotation.Required;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DefaultQuestionService implements QuestionService {
    private QuestionDao questionDao;

    @Override
    public List<QuestionModel> getQuestionsCreatedAfterTime(Date time) {
        if (Objects.nonNull(time)) {
            return questionDao.getQuestionsCreatedAfterTime(time);
        } else {
            return questionDao.getQuestions();
        }
    }

    @Required
    public void setQuestionDao(final QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

}

package de.hybris.training.core.daos.impl;

import de.hybris.training.core.daos.QuestionDao;
import questions.model.QuestionModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("questionDao")
public class DefaultQuestionDao implements QuestionDao {
    private static final String QUERY_SELECT_ALL_QUESTIONS = "SELECT {p:"
            + QuestionModel.PK + "} FROM {"
            + QuestionModel._TYPECODE + " AS p} ";
    private static final String QUERY_SELECT_NEW_QUESTIONS = QUERY_SELECT_ALL_QUESTIONS
            + "WHERE {p:" + QuestionModel.CREATIONTIME + "} > ?" + QuestionModel.CREATIONTIME;

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<QuestionModel> getQuestions() {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(QUERY_SELECT_ALL_QUESTIONS);

        return flexibleSearchService.<QuestionModel>search(query).getResult();
    }

    @Override
    public List<QuestionModel> getQuestionsCreatedAfterTime(Date time) {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(QUERY_SELECT_NEW_QUESTIONS);

        query.addQueryParameter(QuestionModel.CREATIONTIME, time);

        return flexibleSearchService.<QuestionModel>search(query).getResult();
    }
}

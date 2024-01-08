package de.hybris.training.core.services;

import com.myapp.questions.model.QuestionModel;

import java.util.Date;
import java.util.List;

public interface QuestionService {
    List<QuestionModel> getQuestionsCreatedAfterTime(Date date);
}

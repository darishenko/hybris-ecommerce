package com.myapp.questions.populators;

import com.myapp.questions.data.QuestionData;
import com.myapp.questions.model.QuestionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Nonnull;

public class QuestionDataPopulator implements Populator<QuestionModel, QuestionData> {
    @Override
    public void populate(@Nonnull QuestionModel questionModel, @Nonnull QuestionData questionData)
            throws ConversionException {
        questionData.setQuestionCustomer(questionModel.getQuestionCustomer().getName());
        questionData.setQuestion(questionModel.getQuestion());
        questionData.setAnswerCustomer(questionModel.getAnswerCustomer().getName());
        questionData.setAnswer(questionModel.getAnswer());
    }
}

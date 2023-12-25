package com.myapp.questions.populators;

import com.myapp.questions.data.QuestionData;
import com.myapp.questions.model.QuestionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Nonnull;

public class QuestionDataPopulator implements Populator<QuestionModel, QuestionData> {
    @Override
    public void populate(@Nonnull QuestionModel questions, @Nonnull QuestionData questionData)
            throws ConversionException {
        questionData.setQuestionCustomerName(questions.getQuestionCustomer().getName());
        questionData.setQuestion(questions.getQuestion());
        questionData.setAnswerCustomerName(questions.getAnswerCustomer().getName());
        questionData.setAnswer(questions.getAnswer());
    }
}

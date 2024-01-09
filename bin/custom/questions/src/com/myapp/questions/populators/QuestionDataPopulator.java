package com.myapp.questions.populators;

import com.myapp.questions.data.QuestionData;
import com.myapp.questions.model.QuestionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.util.Objects;

public class QuestionDataPopulator implements Populator<QuestionModel, QuestionData> {
    @Override
    public void populate(@Nonnull QuestionModel question, @Nonnull QuestionData questionData) throws ConversionException {
        questionData.setQuestionCustomerName(question.getQuestionCustomer().getName());
        questionData.setQuestion(question.getQuestion());

        if (Objects.nonNull(question.getAnswerCustomer()) && StringUtils.isNotBlank(question.getAnswer())) {
            questionData.setAnswerCustomerName(question.getAnswerCustomer().getName());
            questionData.setAnswer(question.getAnswer());
        }
    }
}

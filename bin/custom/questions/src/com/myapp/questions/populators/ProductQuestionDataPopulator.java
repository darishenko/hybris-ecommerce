package com.myapp.questions.populators;

import com.myapp.questions.data.QuestionData;
import com.myapp.questions.model.QuestionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Nonnull;
import java.util.List;

public class ProductQuestionDataPopulator implements Populator<ProductModel, ProductData> {
    private Converter<QuestionModel, QuestionData> questionConverter;

    @Override
    public void populate(@Nonnull ProductModel productModel, @Nonnull ProductData productData)
            throws ConversionException {
        final List<QuestionModel> questionModelList = productModel.getQuestions();

        productData.setQuestions(questionConverter.convertAll(questionModelList));
    }

    @Required
    public void setQuestionConverter(final Converter<QuestionModel, QuestionData> questionConverter) {
        this.questionConverter = questionConverter;
    }
}

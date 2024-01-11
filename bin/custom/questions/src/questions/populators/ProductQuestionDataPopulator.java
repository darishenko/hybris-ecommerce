package questions.populators;

import com.myapp.questions.data.QuestionData;
import questions.model.QuestionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class ProductQuestionDataPopulator implements Populator<ProductModel, ProductData> {
    private Converter<QuestionModel, QuestionData> questionConverter;

    @Override
    public void populate(@Nonnull ProductModel productModel, @Nonnull ProductData productData)
            throws ConversionException {
        final List<QuestionModel> questions = getApprovedQuestions(productModel);

        productData.setQuestions(questionConverter.convertAll(questions));
    }

    @Required
    public void setQuestionConverter(final Converter<QuestionModel, QuestionData> questionConverter) {
        this.questionConverter = questionConverter;
    }

    private List<QuestionModel> getApprovedQuestions(ProductModel product) {
        return product.getQuestions().stream()
                .filter(item -> Boolean.TRUE.equals(item.getApproved()))
                .collect(Collectors.toList());
    }
}

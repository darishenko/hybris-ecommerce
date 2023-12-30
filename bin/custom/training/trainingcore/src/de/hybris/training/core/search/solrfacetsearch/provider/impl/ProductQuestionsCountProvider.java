package de.hybris.training.core.search.solrfacetsearch.provider.impl;

import com.myapp.questions.model.QuestionModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductQuestionsCountProvider implements FieldValueProvider, Serializable {
    private static final String ERROR_NOT_PRODUCT_TYPE = "Error: item is not a Product type !";

    private FieldNameProvider fieldNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(@Nonnull IndexConfig indexConfig,
                                                 @Nonnull IndexedProperty indexedProperty, @Nonnull Object model)
            throws FieldValueProviderException {
        if (model instanceof ProductModel product) {
            if (indexedProperty.isLocalized()) {
                final Collection<LanguageModel> languages = indexConfig.getLanguages();

                return languages.stream()
                        .flatMap(language -> createFieldValueList(product, language, indexedProperty).stream())
                        .collect(Collectors.toList());
            } else {
                return createFieldValueList(product, null, indexedProperty);
            }
        }

        throw new FieldValueProviderException(ERROR_NOT_PRODUCT_TYPE);
    }

    protected List<FieldValue> createFieldValueList(final ProductModel product, final LanguageModel language,
                                                    final IndexedProperty indexedProperty) {
        final String languageIsoCode = getLanguageIsoCode(language);
        final Integer productQuestionsCount = getProductQuestionsCount(product);
        final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, languageIsoCode);

        return fieldNames.stream()
                .map(fieldName -> new FieldValue(fieldName, productQuestionsCount))
                .collect(Collectors.toList());
    }

    private String getLanguageIsoCode(final LanguageModel language) {
        if (Objects.nonNull(language)) {
            return language.getIsocode();
        }

        return null;
    }

    @Required
    public void setFieldNameProvider(final FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }

    private Integer getProductQuestionsCount(final ProductModel product) {
        List<QuestionModel> questions = product.getQuestions();

        if (CollectionUtils.isEmpty(questions)) {
            return 0;
        }

        return questions.size();
    }
}

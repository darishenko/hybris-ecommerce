package de.hybris.training.facades.populators;


import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;
import de.hybris.platform.core.model.product.ProductModel;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

public class CustomSearchResultVariantProductPopulator extends SearchResultVariantProductPopulator {
    private static final String PRODUCT_QUESTIONS_COUNT = "questionCount";
    private static final Integer DEFAULT_QUESTIONS_COUNT = 0;
    private static final Integer DEFAULT_WARRANTY_YEARS_COUNT = 0;

    @Override
    public void populate(@Nonnull SearchResultValueData source, @Nonnull ProductData target) {
        super.populate(source, target);

        String count = getValue(source, PRODUCT_QUESTIONS_COUNT);

        if (Objects.isNull(count)) {
            target.setQuestionsCount(DEFAULT_QUESTIONS_COUNT);
        }

        target.setQuestionsCount(Integer.valueOf(count));
        target.setWarrantyYears(getProductWarrantyYears(source));
    }

    private Integer getProductWarrantyYears(SearchResultValueData source) {
        return Optional.ofNullable(this.<Integer>getValue(source, ProductModel.WARRANTYYEARS))
                .orElse(DEFAULT_WARRANTY_YEARS_COUNT);
    }
}

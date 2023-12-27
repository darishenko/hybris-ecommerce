package de.hybris.training.facades.populators;


import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultVariantProductPopulator;

import javax.annotation.Nonnull;
import java.util.Objects;

public class CustomSearchResultVariantProductPopulator extends SearchResultVariantProductPopulator {
    private static final String PRODUCT_QUESTIONS_COUNT = "questionCount";

    @Override
    public void populate(@Nonnull SearchResultValueData source, @Nonnull ProductData target) {
        super.populate(source, target);

        Integer count = getValue(source, PRODUCT_QUESTIONS_COUNT);
        if (Objects.isNull(count)) {
            count = 0;
        }
        target.setQuestionsCount(count);
    }
}

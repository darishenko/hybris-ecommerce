package com.myapp.questions.facades;

import de.hybris.platform.commercefacades.product.data.ProductData;

import java.util.Optional;

public interface ProductQuestionFacade {
    Optional<ProductData> getProduct(final String name);
}

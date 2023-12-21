package com.myapp.questions.facades.impl;

import com.myapp.questions.facades.ProductFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class DefaultProductFacade implements ProductFacade {
    private ProductService productService;
    private Converter<ProductModel, ProductData> productConverter;


    @Override
    public Optional<ProductData> getProduct(@NotBlank final String name) {
        final ProductModel productModel = productService.getProductForCode(name);

        return Optional.ofNullable(productModel).map(product -> productConverter.convert(product));
    }

    @Required
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Required
    public void setProductConverter(Converter<ProductModel, ProductData> productConverter) {
        this.productConverter = productConverter;
    }
}

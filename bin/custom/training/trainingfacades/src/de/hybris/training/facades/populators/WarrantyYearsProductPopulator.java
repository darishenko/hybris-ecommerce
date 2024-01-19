package de.hybris.training.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import javax.annotation.Nonnull;
import java.util.Optional;

public class WarrantyYearsProductPopulator implements Populator<ProductModel, ProductData> {
    private static final Integer DEFAULT_WARRANTY_YEARS_COUNT = 0;

    @Override
    public void populate(@Nonnull ProductModel product, @Nonnull ProductData productData) throws ConversionException {
        Integer warrantyYears = Optional.ofNullable(product.getWarrantyYears()).orElse(DEFAULT_WARRANTY_YEARS_COUNT);

        productData.setWarrantyYears(warrantyYears);
    }
}

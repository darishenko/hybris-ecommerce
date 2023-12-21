package com.myapp.questions.controllers.cms;

import com.myapp.questions.controllers.QuestionsControllerConstants;
import com.myapp.questions.facades.ProductFacade;
import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;

import de.hybris.platform.commercefacades.product.data.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.training.core.model.QuestionsCMSComponentModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller("QuestionsCMSComponentController")
@RequestMapping(value = QuestionsControllerConstants.Actions.Cms.QuestionsCMSComponent)
public class QuestionsCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {
    private static final String QUESTIONS = "questions";
    private static final String FONTSIZE = "fontSize";

    private ProductFacade productFacade;

    @Override
    protected void fillModel(HttpServletRequest request, Model model, QuestionsCMSComponentModel component) {
        final String productCode = getRequestContextData(request).getProduct().getCode();

        Optional<ProductData> product = productFacade.getProduct(productCode);

        product.ifPresent(productData -> model.addAttribute(QUESTIONS, productData.getQuestions()));
        model.addAttribute(FONTSIZE, component.getFontSize());
    }

    @Autowired
    public void setProductFacade(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }
}

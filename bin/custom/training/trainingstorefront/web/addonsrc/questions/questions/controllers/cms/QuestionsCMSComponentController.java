package questions.controllers.cms;

import questions.controllers.QuestionsControllerConstants;
import questions.facades.ProductQuestionFacade;
import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import questions.model.QuestionsCMSComponentModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller("QuestionsCMSComponentController")
@RequestMapping(value = QuestionsControllerConstants.Actions.Cms.QuestionsCMSComponent)
public class QuestionsCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {
    private static final String QUESTIONS = "questions";
    private static final String FONTSIZE = "fontSize";

    private ProductQuestionFacade productQuestionFacade;

    @Override
    protected void fillModel(HttpServletRequest request, Model model, QuestionsCMSComponentModel component) {
        Optional<ProductModel> product = Optional.ofNullable(getRequestContextData(request).getProduct());

        if (product.isPresent()) {
            final String productCode = product.get().getCode();

            Optional<ProductData> productData = productQuestionFacade.getProduct(productCode);

            productData.ifPresent(data -> model.addAttribute(QUESTIONS, data.getQuestions()));
        }

        model.addAttribute(FONTSIZE, component.getFontSize());
    }

    @Autowired
    public void setFacade(final ProductQuestionFacade facade) {
        this.productQuestionFacade = facade;
    }
}

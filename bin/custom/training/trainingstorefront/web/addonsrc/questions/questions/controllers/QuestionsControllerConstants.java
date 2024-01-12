package questions.controllers;

import questions.model.QuestionsCMSComponentModel;

public interface QuestionsControllerConstants {
    interface Actions {
        interface Cms {
            String _Prefix = "/view/";
            String _Suffix = "Controller";
            String QuestionsCMSComponent = _Prefix + QuestionsCMSComponentModel._TYPECODE + _Suffix;
        }
    }
}

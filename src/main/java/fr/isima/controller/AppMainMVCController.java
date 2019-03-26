package fr.isima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class AppMainMVCController {

    private QuotesMVCController quotesMVCController;

    @Autowired
    public AppMainMVCController(QuotesMVCController quotesMVCController) {
        this.quotesMVCController = quotesMVCController;
    }

    @GetMapping
    public ModelAndView main() {
        return this.quotesMVCController.getQuote("apps-main", 1);
    }
}

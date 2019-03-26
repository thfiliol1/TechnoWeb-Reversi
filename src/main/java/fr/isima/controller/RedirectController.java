package fr.isima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Just simple redirection
 */
@Controller
public class RedirectController {

    @RequestMapping("/addQuote")
    public ModelAndView addQuote(){
        return new ModelAndView("addQuote");
    }
}

package fr.isima.controller;

import fr.isima.business.Quote;
import fr.isima.business.QuotesService;
import fr.isima.data.QuoteBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/quotes")
public class QuotesMVCController {

    private final QuotesService quotesService;

    @Autowired
    public QuotesMVCController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @GetMapping("{quoteNumber}")
    public ModelAndView displayQuote(@PathVariable(required = false) Integer quoteNumber) {
        return getQuote("quote", quoteNumber);
    }

    @PostMapping(consumes = {"*"})
    public String displayQuote(QuoteForm quoteForm) {
        //we could use buid
        //this kind of code is really easy in Kotlin with named parameter
        final Quote quote = new Quote(quoteForm.getAuthor(), quoteForm.getContent(), quoteForm.getId());
        quotesService.save(quote);
        return "redirect:/main";
    }


    ModelAndView getQuote(String viewName, Integer quoteNumber) {
        return new ModelAndView(viewName, "quote", quotesService.findById(quoteNumber));
    }

}

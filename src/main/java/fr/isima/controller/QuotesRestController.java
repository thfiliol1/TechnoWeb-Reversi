package fr.isima.controller;

import fr.isima.business.Quotes;
import fr.isima.business.QuotesService;
import fr.isima.business.SelectedQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/quotes")
public class QuotesRestController {

    private final QuotesService quotesService;

    @Autowired
    public QuotesRestController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @GetMapping("{quoteNumber}")
    public SelectedQuote displayQuote(@PathVariable Integer quoteNumber) {
        return quotesService.findById(quoteNumber);
    }

    @GetMapping("/")
    public Quotes displayQuote() {
        return quotesService.findAll();
    }

}

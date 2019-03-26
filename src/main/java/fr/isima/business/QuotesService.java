package fr.isima.business;

import fr.isima.data.QuoteBean;
import fr.isima.data.QuotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class QuotesService {

    private final QuotesRepository quotesRepository;

    @Autowired
    public QuotesService(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    public Quotes findAll() {
        return new Quotes(StreamSupport.stream(this.quotesRepository.findAll().spliterator(), false)
                .map(quoteBean -> toQuote(quoteBean)).collect(Collectors.toList()));
    }

    private Quote toQuote(QuoteBean quoteBean) {
        return new Quote(quoteBean.getAuthor(),quoteBean.getContent(),quoteBean.getId());
    }

    public SelectedQuote findById(Integer quoteId) {
        if (quotesRepository.existsById(quoteId)) {
            return new SelectedQuote(findAll(), toQuote(this.quotesRepository.findById(quoteId).get()));
        }
        return createDefaultQuote(quoteId);
    }

    private SelectedQuote createDefaultQuote(Integer numero) {
        final QuoteBean qb = new QuoteBean();
        qb.setAuthor("L'auteur de l'application");
        qb.setContent("Aucune citation n'existe au numero " + numero);
        return new SelectedQuote(findAll(), toQuote(qb));
    }


    public Integer save(Quote quote) {
        final QuoteBean quoteBean = new QuoteBean();
        quoteBean.setAuthor(quote.getAuthor());
        quoteBean.setContent(quote.getContent());
        quoteBean.setId(quote.getId());
        return this.quotesRepository.save(quoteBean).getId();
    }
}

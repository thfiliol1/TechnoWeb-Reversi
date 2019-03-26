package fr.isima.business;

import fr.isima.data.QuoteBean;

/**
 * L'objet immuable contenant les citations
 *
 * @author Benjamin Kuchcik
 * @see Quotes
 */
public class Quote {


    private final String author;

    private final String content;

    private final Integer id;

    public Quote(String author, String content, Integer id) {
        this.author = author;
        this.content = content;
        this.id = id;
    }

    public Quote(String author, String content) {
        this(author,content,null);
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Integer getId() {
        return id;
    }


}

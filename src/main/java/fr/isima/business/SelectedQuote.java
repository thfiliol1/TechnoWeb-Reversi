package fr.isima.business;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

/**
 * La citation selectionn�e : on retrouve la citation est les directions
 * possibles. Certaines sont actives ou non
 *
 * @author Benjamin Kuchcik
 */
public class SelectedQuote {

    static final int FIRST_QUOTE_RANGE = 1;
    private final Quote quote;

    private final Quotes quotes;

    private final Direction first;

    private final Direction next;

    private final Direction last;

    private final Direction previous;

    public SelectedQuote(Quotes quotes, Quote quote) {
        this.quote = requireNonNull(quote, "quote");
        this.quotes = requireNonNull(quotes, "quotes");
        first = first();
        next = next();
        last = last();
        previous = previous();
    }

    /**
     * Utilise le Pattern Builder
     */
    private Direction next() {
        // Pour plus de compr�hension, nous n'allons pas chainer les appel
        // ici
        // On commence pas la base on passe la navigation mod�lis�e et la
        // quote
        // selectionnee
        final DirectionBuilder directionBuilder = new DirectionBuilder(NavigationDirection.NEXT);
        // Ensuite on appelle la m�thode hasNext
        directionBuilder.directionEnabled(() -> hasNext());
        // Enfin on applique la methode pour obtenir l'index de la quote
        // suivante
        directionBuilder.nextTargetQuote(() -> nextQuote());
        // On construit ensuite le builder avec tous les parametress
        return directionBuilder.build();
    }

    private Direction previous() {
        // le fait de chainer des appels comme �a se nomme
        // une fluent API
        return new DirectionBuilder(NavigationDirection.PREVIOUS)
                // on pose la direction
                .directionEnabled(() -> hasPrevious())
                // on renvoie l'index precedent
                .nextTargetQuote(() -> previousIndex()).build();
    }

    private Direction first() {
        return new DirectionBuilder(NavigationDirection.FIRST)
                // le bouton first n'est acitve que si on est pas sur la
                // premiere quote
                .directionEnabled(() -> isNotFirst())
                // Le premier index
                .nextTargetQuote(() -> firstQuoteRange()).build();
    }

    private Direction last() {
        return new DirectionBuilder(NavigationDirection.LAST)
                // Comme pour le first le bouton est inactif que si c'est la
                // derniere quote (ou qu'il y'en a une seule)
                .directionEnabled(() -> isNotLast() && hasAtLeastTwoQuote())
                // Le dernier index tout simplement !
                .nextTargetQuote(() -> lastIndex()).build();
    }

    Quotes getQuotes() {
        return quotes;
    }

    public Direction getFirstDirection() {
        return first;
    }

    public Direction getLastDirection() {
        return last;
    }

    public Direction getNextDirection() {
        return next;
    }

    public Direction getPreviousDirection() {
        return previous;
    }

    public Quote getQuote() {
        return quote;
    }

    private List<Quote> quotes() {
        return quotes.getQuotes();
    }

    boolean hasAtLeastTwoQuote() {
        return quotes.size() > 1;
    }

    public int getNumber() {
        // les citations sont ranges a partir de 1, alors que la liste a parttir
        // de 0
        return indexOfQuote() + 1;
    }

    public long getId() {
        return this.quote.getId();
    }

    public List<Direction> getDirections() {
        return Arrays.asList(getFirstDirection(), getPreviousDirection(), getNextDirection(), getLastDirection());
    }

    private int indexOfQuote() {
        return this.quote.getId() - 1;
    }

    public String getAuthor() {
        return quote.getAuthor();
    }

    public String getContent() {
        return quote.getContent();
    }

    public boolean isNotFirst() {
        return getNumber() != FIRST_QUOTE_RANGE;
    }

    public boolean isNotLast() {
        return getNumber() != quotes.size();
    }

    public boolean hasNext() {
        return getNumber() < getQuotes().size() && hasAtLeastTwoQuote() && getNumber() >= FIRST_QUOTE_RANGE;
    }

    public int nextQuote() {
        return getNumber() + 1;
    }

    public boolean hasPrevious() {
        return getNumber() > FIRST_QUOTE_RANGE && hasAtLeastTwoQuote();
    }

    public int previousIndex() {
        return getNumber() - 1;
    }

    public int firstQuoteRange() {
        return FIRST_QUOTE_RANGE;
    }

    public int lastIndex() {
        return getQuotes().size();
    }

    public class Direction implements DirectionAvailable, IndexOfDirection {

        private final NavigationDirection description;
        private final DirectionAvailable isEnabled;
        private final IndexOfDirection indexOfDirection;

        private Direction(DirectionBuilder builder) {
            requireNonNull(builder.description, "Navigation direction must not be null");
            requireNonNull(builder.isEnabled, "IsEnabled must not be null");
            requireNonNull(builder.indexOfDirection, "getNextTarget must not be null");

            description = builder.description;
            isEnabled = builder.isEnabled;
            indexOfDirection = builder.indexOfDirection;
        }

        public String getTargetLabel() {
            return description.getTargetLabel();
        }

        public String getId() {
            return description.getId();
        }

        @Override
        public int getIndexOfDirection() {
            return indexOfDirection.getIndexOfDirection();
        }

        @Override
        public boolean isEnabled() {
            return isEnabled.isEnabled();
        }

    }

    // le builder va nous permettre de constuire les directions de fa�ons
    // elegantes.
    // https://en.wikipedia.org/wiki/Builder_pattern
    private class DirectionBuilder {

        private final NavigationDirection description;

        private DirectionAvailable isEnabled;
        private IndexOfDirection indexOfDirection;

        DirectionBuilder(NavigationDirection direction) {
            description = direction;
        }

        public DirectionBuilder directionEnabled(DirectionAvailable supplier) {
            isEnabled = supplier;
            return this;
        }

        public DirectionBuilder nextTargetQuote(IndexOfDirection supplier) {
            indexOfDirection = supplier;
            return this;
        }

        public Direction build() {
            return new Direction(this);
        }
    }

}

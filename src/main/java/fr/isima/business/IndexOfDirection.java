package fr.isima.business;

/**
 * Utilse dans une expression lambda
 * 
 * @author Benjamin Kuchcik
 * @see DirectionAvailable
 */
public interface IndexOfDirection {

    /**
     * Permet de retourner l'index de la direction en fonction de la quote passe
     * en parametre.
     * <p>
     * Si l'on pointe sur la direction First, l'index sera
     * {@link SelectedQuote#FIRST_QUOTE_RANGE} ou bien si la direction est next
     * la m�thode retournera {@link SelectedQuote#getNumber() +1 }
     * </p>
     * 
     * @param sq
     *            la quote pass� en param�tre
     * @return l'index de la direction
     */
    int getIndexOfDirection();
}

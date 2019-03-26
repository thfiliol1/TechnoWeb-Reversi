package fr.isima.business;

/**
 * Permet de test si la direction est disponible. Une direction est forc�ment
 * parmis la liste des {@link NavigationDirection}.
 * 
 * <p>
 * L'utilisation de cette interface � une m�thode permet de cr�er des lambas
 * exploit�s dans {@link Direction}
 * </p>
 * 
 * @author Benjamin Kuchcik
 *
 */
public interface DirectionAvailable {
    /**
     * Si la direction est active pour la quote
     * 
     * @param sq
     * @return
     */
    boolean isEnabled();
}

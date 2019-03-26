package fr.isima.business;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represente une session d'utilisation.
 * <p>
 * Un {@link Utilisateur} peut avoir plusieurs sessions d'utilisation en m�me
 * temps. La session d'utilisation est donc relier � un utilisateur. L'objet
 * permet de compter, le nombre de citation vu dans la session d'utilisation en
 * cours.
 * </p>
 * 
 * @author Benjamin Kuchcik
 *
 */
public class SessionUtilisation {

    /**
     * L'utilisateur utilisant utilis� pour se connecter � cette session
     */
    private final Utilisateur utilisateur;

    /**
     * Le nombre de citation vu pour cette session
     */
    private final AtomicInteger nombreDeCitation = new AtomicInteger();

    SessionUtilisation(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public int getNombreDeCitationVue() {
        return nombreDeCitation.get();
    }

    /**
     * Permet d'ajouter une quote � la session d'utilisation. Appel la methode d
     * {@link Utilisateur} pour pouvoir incr�menter le nombre de citation vu par
     * l'utilisateur.
     * 
     * @see Utilisateur#incrementNombreCitationLu()
     */
    public void addAQuote() {
        nombreDeCitation.incrementAndGet();
        utilisateur.incrementNombreCitationLu();
    }

    public void terminer() {
        utilisateur.terminerSession(this);
    }

}

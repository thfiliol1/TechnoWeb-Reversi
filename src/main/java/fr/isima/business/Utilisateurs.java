package fr.isima.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * L'ensemble des utilisateurs de l'application.
 * 
 * Le stockage ne se faisant qu'en m�moire, un red�marrage fait perdre toutes
 * les donn�es
 * 
 * @author Benjamin
 *
 */
public class Utilisateurs {

    private final AtomicInteger nombreTotalCitation = new AtomicInteger(0);

    public void incrementNombreCitationLu() {
        nombreTotalCitation.getAndIncrement();
    }

    public int getNombreCitationVuAuTotal() {
        return nombreTotalCitation.get();
    }

    @Configuration
    public static class UtilisateursConfiguration {

        @Bean
        @ApplicationScope
        public Utilisateurs utilisateurs() {
            return new Utilisateurs();
        }

        @Bean
        @SessionScope
        public SessionUtilisation sessionUtilisation(Utilisateur utilisateur) {
            return utilisateur.ouvrirSessionUtilisation();
        }

        @Bean
        @SessionScope
        @Autowired
        public Utilisateur utilisateur(HttpSession httpSession, QuotesService quotes) {
            return new Utilisateur((String) httpSession.getAttribute("username"), quotes, utilisateurs());
        }
    }
}

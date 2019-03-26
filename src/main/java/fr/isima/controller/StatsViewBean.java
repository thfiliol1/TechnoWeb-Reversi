package fr.isima.controller;

import fr.isima.business.SessionUtilisation;
import fr.isima.business.Utilisateur;
import fr.isima.business.Utilisateurs;

import java.util.Objects;

public class StatsViewBean {

    private final Utilisateurs utilisateurs;
    private final SessionUtilisation sessionUtilisation;

    StatsViewBean(Utilisateurs utilisateurs, SessionUtilisation sessionUtilisation) {
        this.utilisateurs = Objects.requireNonNull(utilisateurs);
        this.sessionUtilisation = Objects.requireNonNull(sessionUtilisation);
    }

    public int getNombreSessionEnCours() {
        return getUtilisateur().getNombreSessionUtilisation();
    }

    private Utilisateur getUtilisateur() {
        return sessionUtilisation.getUtilisateur();
    }

    public int getNombreCitationVuPourLaSessionEnCours() {
        return sessionUtilisation.getNombreDeCitationVue();
    }

    public int getNombreCitationVuAuTotal() {
        return utilisateurs.getNombreCitationVuAuTotal();
    }

    public int getNombreDeCitationPourLUtilisateur() {
        return getUtilisateur().getNombreCitationVue();
    }

}

package fr.isima.controller;

import fr.isima.business.SessionUtilisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import javax.inject.Provider;

@Component
public class UserActivityListener {

    private final Provider<SessionUtilisation> sessionUtilisationProvider;

    @Autowired
    public UserActivityListener(
            Provider<SessionUtilisation> sessionUtilisationProvider) {
        this.sessionUtilisationProvider = sessionUtilisationProvider;
    }

    @EventListener
    public void handleEvent(ServletRequestHandledEvent e) {
        if (e.getRequestUrl().contains("quotes")) {
            this.sessionUtilisationProvider.get().addAQuote();
        }
    }
}

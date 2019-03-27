package fr.isima.controller;

import fr.isima.business.MyUserPrincipal;
import fr.isima.business.PlayerService;
import fr.isima.data.PlayerBean;
import org.aspectj.apache.bcel.util.Play;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("list-connected-users")
public class ListConnectedUserMVCController {

    private final PlayerService playerService;


    @Autowired
    public ListConnectedUserMVCController(PlayerService playerService) {
        this.playerService = playerService;
    }

    public ModelAndView getConnectedUsers(String viewName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((MyUserPrincipal)authentication.getPrincipal()).getUsername();
        return new ModelAndView(viewName,"listAvailablePlayers",playerService.findPlayersReadyToPlay(username));
    }
}

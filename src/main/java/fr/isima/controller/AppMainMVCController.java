package fr.isima.controller;

import fr.isima.business.MyUserPrincipal;
import fr.isima.business.PlayerService;
import fr.isima.data.PlayerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/main")
public class AppMainMVCController {

    private static final String APPS_MAIN_VIEW_NAME = "apps-main";

    private ListConnectedUserMVCController listConnectedUserMVCController;
    private PlayerService playerService;

    @Autowired
    public AppMainMVCController(ListConnectedUserMVCController listConnectedUserMVCController, PlayerService playerService) {
        this.listConnectedUserMVCController = listConnectedUserMVCController;
        this.playerService = playerService;
    }

    @GetMapping
    public ModelAndView main() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerBean player = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();
        playerService.setUserIsPlaying(player.getUsername(),false);

        return this.listConnectedUserMVCController.getConnectedUsers(APPS_MAIN_VIEW_NAME);
    }
}

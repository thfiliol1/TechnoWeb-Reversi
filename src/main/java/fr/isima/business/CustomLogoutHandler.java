package fr.isima.business;

import fr.isima.data.GameBean;
import fr.isima.data.PlayerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if(authentication != null) {
            playerService.setUserIsConnected(authentication.getName(),false);
            playerService.setUserIsPlaying(authentication.getName(),false);
            PlayerBean player = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();
            GameBean game = gameService.gameOfPlayer(player);
            if(game != null){
                gameService.endOfGame(game);
                gameService.declareDefeat(game, player);
            }
        }
    }
}

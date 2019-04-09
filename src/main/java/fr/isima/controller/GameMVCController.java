package fr.isima.controller;

import fr.isima.business.GameService;
import fr.isima.business.MyUserPrincipal;
import fr.isima.business.PlayerService;
import fr.isima.data.BoxBean;
import fr.isima.data.GameBean;
import fr.isima.data.PlayerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameMVCController {

    private static final String GAME_VIEW_NAME = "game";

    private static final String GRID_VIEW_NAME = "grille";

    private static final String LIST_GAME_BOXES_NAME = "listGameBoxes";

    private static final String CURRENT_PLAYER_ID_BOXES_NAME = "currentPlayerId";

    private final GameService gameService;

    private final PlayerService playerService;

    @Autowired
    public GameMVCController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @PostMapping
    public ModelAndView play(@RequestParam("selectedPlayer") PlayerBean selectedPlayer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerBean player1 = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();
        GameBean game = gameService.createGame(player1,selectedPlayer);
        List<BoxBean> boxes = gameService.getAllGridBoxes(game);
        int nbPawn1 = gameService.getNbPawnByPlayer(game, game.getIdPlayer1());
        int nbPawn2 = gameService.getNbPawnByPlayer(game, game.getIdPlayer2());
        return new ModelAndView(GAME_VIEW_NAME,LIST_GAME_BOXES_NAME,boxes).addObject("player1",game.getIdPlayer1().getUsername())
                .addObject("nbPawn1",nbPawn1)
                .addObject("player2",game.getIdPlayer2().getUsername())
                .addObject("nbPawn2",nbPawn2);
    }

    @GetMapping
    public ModelAndView play(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerBean player = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();
        GameBean game = gameService.gameOfPlayer(player);
        List<BoxBean> boxes = gameService.getAllGridBoxes(game);
        int nbPawn1 = gameService.getNbPawnByPlayer(game, game.getIdPlayer1());
        int nbPawn2 = gameService.getNbPawnByPlayer(game, game.getIdPlayer2());
        return new ModelAndView(GAME_VIEW_NAME,LIST_GAME_BOXES_NAME,boxes).addObject("player1",game.getIdPlayer1().getUsername())
                .addObject("nbPawn1",nbPawn1)
                .addObject("player2",game.getIdPlayer2().getUsername())
                .addObject("nbPawn2",nbPawn2);
    }

    @GetMapping("/grid")
    public ModelAndView displayGrid(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerBean player = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();
        GameBean game = gameService.gameOfPlayer(player);
        if(game==null){
            game=gameService.lastGameOfPlayer(player);
        }
        List<BoxBean> boxes = gameService.getAllGridBoxes(game);
        int nbPawn1 = gameService.getNbPawnByPlayer(game, game.getIdPlayer1());
        int nbPawn2 = gameService.getNbPawnByPlayer(game, game.getIdPlayer2());
        return new ModelAndView(GRID_VIEW_NAME,LIST_GAME_BOXES_NAME,boxes).addObject("player1",game.getIdPlayer1().getUsername())
                .addObject("nbPawn1",nbPawn1)
                .addObject("player2",game.getIdPlayer2().getUsername())
                .addObject("nbPawn2",nbPawn2);
    }



   /* @GetMapping("/started")
    public ModelAndView gameIsStarted() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((MyUserPrincipal)authentication.getPrincipal()).getUsername();
        return new ModelAndView("apps-main","listAvailablePlayers",playerService.findPlayersReadyToPlay(username));
    }*/
}

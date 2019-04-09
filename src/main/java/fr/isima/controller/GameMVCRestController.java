package fr.isima.controller;


import fr.isima.business.Direction;
import fr.isima.business.GameService;
import fr.isima.business.MyUserPrincipal;
import fr.isima.data.BoxBean;
import fr.isima.data.GameBean;
import fr.isima.data.PlayerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest/game")
public class GameMVCRestController {

    private final GameService gameService;

    @Autowired
    public GameMVCRestController(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping("/started")
    public Boolean gameIsStarted() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerBean player = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();

        return this.gameService.gameIsStarted(player);
    }

    @GetMapping("/canPlay")
    public int playerCanPlay() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerBean player = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();
        GameBean game = gameService.gameOfPlayer(player);
        int code = gameService.playerCanPlay(player);
        if( code == 1){
            if(!gameService.existPossibleMove(game, player)) {
                gameService.changePlayer(game, player);
                code=0;
            }
        }
        return code;

    }

    @GetMapping("/play")
    public int putPawn(@RequestParam("idBox") int idBox){
        int nbMove=0, ind;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerBean player = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();
        BoxBean boxSelected = gameService.getBox(idBox);
        GameBean game = gameService.gameOfPlayer(player);

        if (boxSelected.getIdPlayer() == null){
            //if(gameService.existPossibleMove(game, player)) {
               if (gameService.moveIsPossible(boxSelected, player, game)) {
                    gameService.putPawn(boxSelected.getyPosition(), boxSelected.getxPosition(), player, game);
                    nbMove = gameService.nbReturnPawnBydirection(boxSelected, player, game, Direction.NORD_OUEST);
                    for (ind = 1; ind < nbMove + 1; ind++) {
                        gameService.putPawn(boxSelected.getyPosition() - ind, boxSelected.getxPosition() - ind, player, game);
                    }
                    nbMove = gameService.nbReturnPawnBydirection(boxSelected, player, game, Direction.NORD);
                    for (ind = 1; ind < nbMove + 1; ind++) {
                        gameService.putPawn(boxSelected.getyPosition() - ind, boxSelected.getxPosition(), player, game);
                    }
                    nbMove = gameService.nbReturnPawnBydirection(boxSelected, player, game, Direction.NORD_EST);
                    for (ind = 1; ind < nbMove + 1; ind++) {
                        gameService.putPawn(boxSelected.getyPosition() - ind, boxSelected.getxPosition() + ind, player, game);
                    }
                    nbMove = gameService.nbReturnPawnBydirection(boxSelected, player, game, Direction.OUEST);
                    for (ind = 1; ind < nbMove + 1; ind++) {
                        gameService.putPawn(boxSelected.getyPosition(), boxSelected.getxPosition() - ind, player, game);
                    }
                    nbMove = gameService.nbReturnPawnBydirection(boxSelected, player, game, Direction.EST);
                    for (ind = 1; ind < nbMove + 1; ind++) {
                        gameService.putPawn(boxSelected.getyPosition(), boxSelected.getxPosition() + ind, player, game);
                    }
                    nbMove = gameService.nbReturnPawnBydirection(boxSelected, player, game, Direction.SUD_OUEST);
                    for (ind = 1; ind < nbMove + 1; ind++) {
                        gameService.putPawn(boxSelected.getyPosition() + ind, boxSelected.getxPosition() - ind, player, game);
                    }
                    nbMove = gameService.nbReturnPawnBydirection(boxSelected, player, game, Direction.SUD);
                    for (ind = 1; ind < nbMove + 1; ind++) {
                        gameService.putPawn(boxSelected.getyPosition() + ind, boxSelected.getxPosition(), player, game);
                    }
                    nbMove = gameService.nbReturnPawnBydirection(boxSelected, player, game, Direction.SUD_EST);
                    for (ind = 1; ind < nbMove + 1; ind++) {
                        gameService.putPawn(boxSelected.getyPosition() + ind, boxSelected.getxPosition() + ind, player, game);
                    }

                    if (gameService.getNbBoxesEmpty(game) != 0) {
                        gameService.changePlayer(game, player);
                        return 1;
                    } else {
                        gameService.endOfGame(game);
                        return 0;
                    }

                } else {

                    return -1;
                }
           /* }
            else{
                //aucune possibilité
                gameService.changePlayer(game, player);
                return 1;
            }*/
        }
        else{
            return -1;
        }

    }

    @GetMapping("/winner")
    public int isWinner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PlayerBean player = ((MyUserPrincipal)authentication.getPrincipal()).getPlayer();
        GameBean game = gameService.lastGameOfPlayer(player);

        int nbPawn = gameService.getNbPawnByPlayer(game, player);

        if(nbPawn > 32){
            return 1;
        }
        else if(nbPawn == 32){
            return 0;
        }
        else{
            return -1;
        }
    }

}

package fr.isima.business;

import fr.isima.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameService {

    private final GameRepository gameRepository;

    private final BoxRepository boxRepository;

    private final PlayerRepository playerRepository;

    @Autowired
    public GameService(GameRepository gameRepository, BoxRepository boxRepository, PlayerRepository playerRepository) {
        this.gameRepository = gameRepository;
        this.boxRepository = boxRepository;
        this.playerRepository = playerRepository;
    }

    public GameBean createGame(final PlayerBean player1, final PlayerBean player2) {
        playerRepository.updatePlayerWhenGameStarting(player1.getId(),false);
        playerRepository.updatePlayerWhenGameStarting(player2.getId(),true);
        GameBean game = new GameBean();
        game.setIdPlayer1(player1);
        game.setIdPlayer2(player2);
        game.setPlaying(player1.getId());
        game = gameRepository.save(game);
        BoxBean tempBox;
        for(int x = 0; x <= 7; x++) {
           for(int y = 0; y <= 7 ; y++) {
               tempBox = new BoxBean();
               tempBox.setIdGame(game);
               tempBox.setxPosition(x);
               tempBox.setyPosition(y);
               if(x == 3 && y == 3 || x == 4 && y == 4) {tempBox.setIdPlayer(player1);}
               if(x == 3 && y == 4 || x == 4 && y == 3) {tempBox.setIdPlayer(player2);}
               boxRepository.save(tempBox);
           }
        }
        return game;
    }

    public List<BoxBean> getAllGridBoxes(final GameBean game) {
        return boxRepository.findAllGridBoxesWithGameId(game);
    }

    public BoxBean getBox(int idBox){
        return boxRepository.getById(idBox);
    }

    public Boolean gameIsStarted(PlayerBean player){
        if (gameRepository.gameOfPlayer(player) != null){
            return true;
        }
        else{
            return false;
        }
    }

    public GameBean gameOfPlayer(PlayerBean player){
        return gameRepository.gameOfPlayer(player);
    }


    public GameBean lastGameOfPlayer(PlayerBean player){
        return gameRepository.lastGameOfPlayer(player).get(0);
    }

    public int playerCanPlay(PlayerBean player){
        GameBean game = gameRepository.gameOfPlayer(player);
        if(game != null) {
            if (game.getPlaying() == player.getId()) {
                return 1;
            } else {
                return 0;
            }
        }
        else{
            return -1;
        }
    }

    public boolean moveIsPossible(BoxBean boxSelected, PlayerBean player, GameBean game){
        int nbPawn = 0;
        nbPawn = nbReturnPawnBydirection(boxSelected,player,game,Direction.NORD_OUEST);
        nbPawn += nbReturnPawnBydirection(boxSelected,player,game,Direction.NORD);
        nbPawn += nbReturnPawnBydirection(boxSelected,player,game,Direction.NORD_EST);
        nbPawn += nbReturnPawnBydirection(boxSelected,player,game,Direction.OUEST);
        nbPawn += nbReturnPawnBydirection(boxSelected,player,game,Direction.EST);
        nbPawn += nbReturnPawnBydirection(boxSelected,player,game,Direction.SUD_OUEST);
        nbPawn += nbReturnPawnBydirection(boxSelected,player,game,Direction.SUD);
        nbPawn += nbReturnPawnBydirection(boxSelected,player,game,Direction.SUD_EST);
        if(nbPawn>0){
            return true;
        }
        else{
            return false;
        }
    }

    public int nbReturnPawnBydirection(BoxBean boxSelected, PlayerBean player, GameBean game, Direction direction){
        int nb=0, i=0, j=0, posY, posX;
        switch(direction){
            case NORD_OUEST :
                i=-1;
                j=-1;
                break;
            case NORD:
                i=-1;
                j=0;
                break;
            case NORD_EST:
                i=-1;
                j=1;
                break;
            case OUEST:
                i=0;
                j=-1;
                break;
            case EST:
                i=0;
                j=1;
                break;
            case SUD_OUEST:
                i=1;
                j=-1;
                break;
            case SUD:
                i=1;
                j=0;
                break;
            case SUD_EST:
                i=1;
                j=1;
                break;

        }
        posY = boxSelected.getyPosition()+i;
        posX = boxSelected.getxPosition()+j;
        try {
            BoxBean nextBox = boxRepository.getByPosYAndPosX(posY, posX, game);
            while (nextBox != null && nextBox.getIdPlayer() != null && nextBox.getIdPlayer().getId() != player.getId()) {
                nb++;
                posY += i;
                posX += j;
                nextBox = boxRepository.getByPosYAndPosX(posY, posX, game);
            }

            //si case vide remettre compteur à 0
            if (nextBox.getIdPlayer() == null) {
                nb = 0;
            }
        }
        catch (NullPointerException e){
            nb=0;
        }

        return nb;
    }

    public void putPawn(int posY, int posX, PlayerBean player, GameBean game){
        boxRepository.updateBox(posY,posX,player,game);
    }

    public void changePlayer(GameBean game, PlayerBean player){
        PlayerBean adv;
        if (game.getIdPlayer1().getId() == player.getId()){
            adv = game.getIdPlayer2();
        }
        else{
            adv = game.getIdPlayer1();
        }
        gameRepository.updateGame(game.getId(), adv.getId());
    }

    public int getNbPawnByPlayer(GameBean game, PlayerBean player){
        return boxRepository.getBoxesPlayer(game, player).size();
    }

    public int getNbBoxesEmpty(GameBean game){
        return boxRepository.getBoxesEmpty(game).size();
    }

    public void endOfGame(GameBean game){
        gameRepository.updateGame(game.getId(), -1);
    }

    public boolean existPossibleMove(GameBean game, PlayerBean player){
        List<BoxBean> boxesEmpty = boxRepository.getBoxesEmpty(game);
        for (BoxBean box: boxesEmpty) {
            if(moveIsPossible(box,player,game)){
                return true;
            }
        }
        return false;
    }

    public void declareDefeat(GameBean game, PlayerBean player){
        PlayerBean adv;
        if (game.getIdPlayer1().getId() == player.getId()){
            adv = game.getIdPlayer2();
        }
        else{
            adv = game.getIdPlayer1();
        }

        boxRepository.updateBoxes(game,adv);

    }
}

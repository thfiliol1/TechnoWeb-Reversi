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
}

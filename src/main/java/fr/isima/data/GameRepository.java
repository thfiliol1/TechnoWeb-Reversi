package fr.isima.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface GameRepository extends CrudRepository<GameBean, Integer> {

    @Query("SELECT game FROM GameBean game WHERE (game.idPlayer1 = :player OR game.idPlayer2 = :player) AND game.playing != -1")
    GameBean gameOfPlayer(@Param("player") PlayerBean player);

    @Query("SELECT game FROM GameBean game WHERE (game.idPlayer1 = :player OR game.idPlayer2 = :player) AND game.playing = -1 ORDER BY game.id DESC")
    List<GameBean> lastGameOfPlayer(@Param("player") PlayerBean player);

    @Modifying
    @Transactional
    @Query(value = "UPDATE GameBean game SET game.playing= :player WHERE game.id = :actualGame")
    void updateGame(@Param("actualGame") int actualGame, @Param("player") int player);


}

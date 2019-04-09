package fr.isima.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface BoxRepository extends CrudRepository<BoxBean, Integer> {

    @Query("SELECT box FROM BoxBean box WHERE box.idGame = :game")
    List<BoxBean> findAllGridBoxesWithGameId(@Param("game") final GameBean game);

    @Query("SELECT box FROM BoxBean box WHERE box.id = :idBox")
    BoxBean getById(@Param("idBox") int idBox);

    @Query("SELECT box FROM BoxBean box WHERE box.yPosition = :yPosition AND box.xPosition = :xPosition AND box.idGame = :game")
    BoxBean getByPosYAndPosX(@Param("yPosition") int yPosition, @Param("xPosition") int xPosition, @Param("game") GameBean game);

    @Query("SELECT box FROM BoxBean box WHERE box.idGame = :game AND box.idPlayer = :player")
    List<BoxBean> getBoxesPlayer(@Param("game") GameBean game, @Param("player") PlayerBean player);

    @Query("SELECT box FROM BoxBean box WHERE box.idGame = :game AND box.idPlayer = NULL")
    List<BoxBean> getBoxesEmpty(@Param("game") GameBean game);

    @Modifying
    @Transactional
    @Query(value = "UPDATE BoxBean box SET box.idPlayer = :player WHERE box.yPosition = :posY AND box.xPosition = :posX AND box.idGame = :game")
    void updateBox(@Param("posY") int posY, @Param("posX") int posX, @Param("player") PlayerBean player, @Param("game") GameBean game);

}

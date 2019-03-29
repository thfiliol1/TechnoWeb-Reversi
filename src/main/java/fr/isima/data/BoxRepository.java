package fr.isima.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoxRepository extends CrudRepository<BoxBean, Integer> {

    @Query("SELECT box FROM BoxBean box WHERE box.idGame = :game")
    List<BoxBean> findAllGridBoxesWithGameId(@Param("game") final GameBean game);
}

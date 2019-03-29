package fr.isima.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


public interface PlayerRepository extends CrudRepository<PlayerBean, Integer> {
    PlayerBean findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PlayerBean pla SET pla.isConnected = :isConnected WHERE pla.username = :username")
    void updateIsUserConnected(@Param("username") String username, @Param("isConnected") Boolean isConnected);

    @Query("SELECT pla FROM PlayerBean pla WHERE pla.isConnected = true AND pla.isPlaying = false AND pla.username != :username")
    List<PlayerBean> findPlayersReadyToPlay(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PlayerBean pla SET pla.color = :color, pla.isPlaying = true WHERE pla.id = :playerId")
    void updatePlayerWhenGameStarting(@Param("playerId") Integer playerId, @Param("color") Boolean color);


}

package fr.isima.data;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<PlayerBean, Integer> {
    PlayerBean findByUsername(String username);
}

package fr.isima.business;

import fr.isima.data.PlayerBean;
import fr.isima.data.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlayerService implements UserDetailsService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        PlayerBean player = playerRepository.findByUsername(username);
        if(player == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(player);
    }

    public void setUserIsConnected(String username, Boolean isConnected) {
        playerRepository.updateIsUserConnected(username,isConnected);
    }

    public List<PlayerBean> findPlayersReadyToPlay(String username) {
        return playerRepository.findPlayersReadyToPlay(username);
    }


}

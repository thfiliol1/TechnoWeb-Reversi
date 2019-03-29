package fr.isima.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "GAME")
public class GameBean implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Foreign key for the first player
     */
    @OneToOne
    private PlayerBean idPlayer1;

    /**
     * Foreign key for the second player
     */
    @OneToOne
    private PlayerBean idPlayer2;

    /**
     * Who is playing ?
     * -1 if game terminated OR Id of the current player
     */
    @Column(name = "playing")
    private Integer playing;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlayerBean getIdPlayer1() {
        return idPlayer1;
    }

    public void setIdPlayer1(PlayerBean idPlayer1) {
        this.idPlayer1 = idPlayer1;
    }

    public PlayerBean getIdPlayer2() {
        return idPlayer2;
    }

    public void setIdPlayer2(PlayerBean idPlayer2) {
        this.idPlayer2 = idPlayer2;
    }

    public Integer getPlaying() {
        return playing;
    }

    public void setPlaying(Integer playing) {
        this.playing = playing;
    }
}

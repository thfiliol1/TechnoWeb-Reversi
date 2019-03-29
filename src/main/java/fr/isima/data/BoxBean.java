package fr.isima.data;

import javax.persistence.*;

@Entity
@Table(name = "BOX")
public class BoxBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Foreign key for the player on the box (Null if the box is empty)
     */
    @OneToOne
    private PlayerBean idPlayer;

    /**
     * Foreign key for the game
     */
    @OneToOne
    private GameBean idGame;

    /**
     * X Position
     */
    @Column(name = "X_POSITION")
    private Integer xPosition;

    /**
     * Y Position
     */
    @Column(name = "Y_POSITION")
    private Integer yPosition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlayerBean getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(PlayerBean idPlayer) {
        this.idPlayer = idPlayer;
    }

    public GameBean getIdGame() {
        return idGame;
    }

    public void setIdGame(GameBean idGame) {
        this.idGame = idGame;
    }

    public Integer getxPosition() {
        return xPosition;
    }

    public void setxPosition(Integer xPosition) {
        this.xPosition = xPosition;
    }

    public Integer getyPosition() {
        return yPosition;
    }

    public void setyPosition(Integer yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public String toString() {
        return idPlayer == null ? "vide" : idPlayer.getId().toString();
    }
}
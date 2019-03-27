package fr.isima.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "PLAYER")
public class PlayerBean implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    /**
     * False if user is not playing or true otherwise
     */
    @Column(name = "is_playing")
    private Boolean isPlaying;

    /**
     * False if user is not connected or true otherwise
     */
    @Column(name = "is_connected")
    private Boolean isConnected;

    /**
     * False if color is white or true otherwise
     */
    @Column(name = "color")
    private Boolean color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(Boolean playing) {
        isPlaying = playing;
    }

    public Boolean getConnected() {
        return isConnected;
    }

    public void setConnected(Boolean connected) {
        isConnected = connected;
    }

    public Boolean getColor() {
        return color;
    }

    public void setColor(Boolean color) {
        this.color = color;
    }
}
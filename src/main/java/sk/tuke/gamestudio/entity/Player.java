package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NamedQuery(name = "Player.getPlayers",
        query = "SELECT p FROM Player p ORDER BY p.name")
@NamedQuery(name = "Player.resetPlayers",
        query = "DELETE FROM Player")
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='[HIDDEN]'" +
                '}';
    }
}

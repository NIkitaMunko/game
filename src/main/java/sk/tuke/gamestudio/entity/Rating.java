package sk.tuke.gamestudio.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Setter
@Getter
@Entity
@NamedQuery(name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
@NamedQuery(name = "Rating.resetRatings",
        query = "DELETE FROM Rating")
@NamedQuery(name = "Rating.getRatingPlayer",
        query = "SELECT r FROM Rating r WHERE r.game = :game AND r.player = :player")
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue
    private int ident;

    private String game;

    private String player;

    private int rating;

    @Column(name = "ratedon", nullable = false)
    private Date ratedOn;

    public Rating(String game, String player, int rating, Date ratedOn) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    @Override
    public String toString() {
        return "Score{" +
                "game='" + game + '\'' +
                ", player='" + player + '\'' +
                ", rating=" + rating +
                ", ratedOn=" + ratedOn +
                '}';
    }
}

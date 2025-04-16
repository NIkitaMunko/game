package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        try {
            Rating existingRating = entityManager.createNamedQuery("Rating.getRatingPlayer", Rating.class)
                    .setParameter("game", rating.getGame())
                    .setParameter("player", rating.getPlayer())
                    .getSingleResult();

            if (existingRating != null) {
                existingRating.setRating(rating.getRating());
                existingRating.setRatedOn(rating.getRatedOn());
            }
        } catch (Exception ignored) {
            entityManager.persist(rating);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double avg = entityManager.createNamedQuery("Rating.getAverageRating", Double.class)
                .setParameter("game", game)
                .getSingleResult();
        return avg != null ? avg.intValue() : 0;
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            Rating rating = entityManager.createNamedQuery("Rating.getRatingPlayer", Rating.class)
                    .setParameter("game", game)
                    .setParameter("player", player)
                    .getSingleResult();
            return rating != null ? rating.getRating() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
    }
}

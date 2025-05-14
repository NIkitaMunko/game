package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class PlayerServiceJPA implements PlayerService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addPlayer(Player player) throws PlayerException {
        entityManager.persist(player);
    }

    @Override
    public List<Player> getPlayers() throws PlayerException {
        return entityManager.createQuery("SELECT p FROM Player p", Player.class)
                .getResultList();
    }

    @Override
    public void reset() throws PlayerException {
        entityManager.createQuery("DELETE FROM Player").executeUpdate();
    }
}

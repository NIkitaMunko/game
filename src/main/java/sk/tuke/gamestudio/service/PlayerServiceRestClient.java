package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Player;

import java.util.Arrays;
import java.util.List;

public class PlayerServiceRestClient implements PlayerService {
    private final String url = "http://localhost:8080/api/player";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addPlayer(Player player) throws PlayerException {
        restTemplate.postForEntity(url, player, Void.class);
    }

    @Override
    public List<Player> getPlayers() {
        return Arrays.asList(restTemplate.getForEntity(url, Player[].class).getBody());
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
    }
}
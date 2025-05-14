package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerServiceJDBC implements PlayerService {
    private static final String URL = "jdbc:postgresql://localhost/postgres";
    private static final String USER = "nikitamunko";
    private static final String PASSWORD = "";
    private static final String SELECT = "SELECT name, password FROM player";
    private static final String DELETE = "DELETE FROM player";
    private static final String INSERT = "INSERT INTO player (name, password) VALUES (?, ?)";

    @Override
    public void addPlayer(Player player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT)
        ) {
            statement.setString(1, player.getName());
            statement.setString(2, player.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PlayerException("Problem inserting player", e);
        }
    }

    @Override
    public List<Player> getPlayers() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(SELECT)
        ) {
            List<Player> players = new ArrayList<>();
            while (rs.next()) {
                Player player = new Player();
                player.setName(rs.getString("name"));
                player.setPassword(rs.getString("password"));
                players.add(player);
            }
            return players;
        } catch (SQLException e) {
            throw new PlayerException("Problem selecting all players", e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new PlayerException("Problem deleting comments", e);
        }
    }
}

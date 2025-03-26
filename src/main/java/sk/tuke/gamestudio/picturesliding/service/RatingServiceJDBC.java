package sk.tuke.gamestudio.picturesliding.service;

import sk.tuke.gamestudio.picturesliding.entity.Rating;
import java.sql.*;

public class RatingServiceJDBC implements RatingService {
    private static final String URL = "jdbc:postgresql://localhost/postgres";
    private static final String USER = "nikitamunko";
    private static final String PASSWORD = "";
    private static final String SELECT_AVG = "SELECT AVG(rating) FROM rating WHERE game = ?";
    private static final String SELECT_PLAYER = "SELECT rating FROM rating WHERE game = ? AND player = ?";
    private static final String DELETE = "DELETE FROM rating";
    private static final String INSERT_OR_UPDATE =
            "INSERT INTO rating (game, player, rating, ratedOn) " + "VALUES (?, ?, ?, ?) " +
            "ON CONFLICT (game, player) " + "DO UPDATE SET rating = EXCLUDED.rating, ratedOn = EXCLUDED.ratedOn";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(INSERT_OR_UPDATE)
        ) {
            statement.setString(1, rating.getGame());
            statement.setString(2, rating.getPlayer());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RatingException("Problem inserting/updating rating", e);
        }
    }

    @Override
    public int getAverageRating(String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_AVG)
        ) {
            statement.setString(1, game);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting average rating", e);
        }
    }

    @Override
    public int getRating(String game, String player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(SELECT_PLAYER)
        ) {
            statement.setString(1, game);
            statement.setString(2, player);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RatingException("Problem selecting player rating", e);
        }
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new RatingException("Problem deleting ratings", e);
        }
    }
}

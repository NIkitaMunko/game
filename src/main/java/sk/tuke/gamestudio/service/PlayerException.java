package sk.tuke.gamestudio.service;

import java.sql.SQLException;

public class PlayerException extends RuntimeException {
    public PlayerException(String message, SQLException e) {
        super(message);
    }
}

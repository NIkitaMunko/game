package core;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.picturesliding.core.Tile;

import static org.junit.jupiter.api.Assertions.*;

public class GameFieldTest {

    @Test
    void testGameFieldInitialization() {
        GameField gameField = new GameField(3, 3);
        assertNotNull(gameField.getFieldArray(), "Field array should not be null");
    }

    @Test
    void testTileInitialization() {
        GameField gameField = new GameField(3, 3);
        Tile[][] field = gameField.getFieldArray();
        assertEquals(9, field.length * field[0].length, "Field should have 9 tiles");
    }

    @Test
    void testGameSolvedCondition() {
        GameField gameField = new GameField(3, 3);
        assertFalse(gameField.isSolved(), "Game should not be solved immediately after initialization");
    }
}

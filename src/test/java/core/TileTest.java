package core;


import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.picturesliding.core.Tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileTest {

    @Test
    void testTileValue() {
        Tile tile = new Tile(5);
        assertEquals(5, tile.getPiece(), "Tile value should be set correctly");
    }

    @Test
    void testEmptyTile() {
        Tile tile = new Tile(0);
        assertTrue(tile.isEmpty(), "Tile with value 0 should be empty");
    }
}

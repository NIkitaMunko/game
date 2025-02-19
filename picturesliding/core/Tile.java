package picturesliding.core;

public class Tile {
    private final int picturePiece;

    public Tile(int value) {
        this.picturePiece = value;
    }

    public int getPiece() {
        return picturePiece;
    }

    public boolean isEmpty() {
        return picturePiece == 0;
    }

}

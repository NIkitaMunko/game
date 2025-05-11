package sk.tuke.gamestudio.picturesliding.core;

public class Tile {
    private int picturePiece;

    public Tile(int value) {
        this.picturePiece = value;
    }

    public int getPiece() {
        return picturePiece;
    }

    public void setPiece(int value) {
        picturePiece = value;
    }

    public boolean isEmpty() {
        return picturePiece == 0;
    }

}

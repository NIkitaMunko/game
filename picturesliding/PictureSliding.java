package picturesliding;

import picturesliding.consoleui.ConsoleUI;
import picturesliding.core.GameField;

public class PictureSliding {

    public static void main(String[] args) {
        GameField gameField = new GameField(3, 3);
        ConsoleUI consoleUI = new ConsoleUI(gameField);
        consoleUI.play();
        ConsoleUI.closeScanner();
    }
}

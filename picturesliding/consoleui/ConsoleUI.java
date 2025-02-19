package picturesliding.consoleui;

import picturesliding.core.GameField;
import picturesliding.core.GameState;
import picturesliding.core.Tile;

import java.util.Scanner;

public class ConsoleUI {

    private GameField field;
    private Scanner scanner;

    public ConsoleUI(int rows, int columns) {
        this.field = new GameField(rows, columns);
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        while (!field.isSolved() && field.getState() == GameState.PLAYING) {
            printField();
            int input = handleInput();
            field.movePicture(input);
        }
        printField();
        System.out.println("Congratulations! You solved the puzzle.");
        scanner.close();
    }

    private int handleInput() {
        System.out.print("Enter a number to move: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return handleInput();
        }
    }

    private void printField() {
        Tile[][] fieldArray = field.getFieldArray();
        for (Tile[] row : fieldArray) {
            for (Tile tile : row) {
                System.out.printf("%3d ", tile.getPiece());
            }
            System.out.println();
        }
    }
}

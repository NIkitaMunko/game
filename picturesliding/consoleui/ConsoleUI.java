package picturesliding.consoleui;

import picturesliding.core.GameField;
import picturesliding.core.GameState;
import picturesliding.core.Tile;

import java.util.Scanner;

public class ConsoleUI {

    private final GameField field;
    private static final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(GameField field) {
        this.field = field;
    }

    public void play() {
        while (!field.isSolved() && field.getState() == GameState.PLAYING) {
            printField();
            int input = handleInput();
            field.moveTile(input);
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
        int columns = fieldArray[0].length;
        for (Tile[] row : fieldArray) {
            System.out.println("-".repeat(columns * 5 + 1));
            for (Tile tile : row) {
                System.out.printf("| %2d ", tile.getPiece());
            }
            System.out.println("|");
        }
        System.out.println("-".repeat(columns * 5 + 1));
    }

    public static void closeScanner() {
        scanner.close();
    }

}

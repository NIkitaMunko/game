package picturesliding.consoleui;

import picturesliding.core.GameField;
import picturesliding.core.GameState;
import picturesliding.core.Tile;

import java.util.Scanner;

public class ConsoleUI {

    private final GameField field;
    private static final Scanner scanner = new Scanner(System.in);

    private static final String RESET = "\033[0m";
    private static final String[] COLORS = {
            "\033[90m",
            "\033[31m",
            "\033[32m",
            "\033[33m",
            "\033[34m",
            "\033[35m",
            "\033[36m",
            "\033[91m",
            "\033[92m",
            "\033[93m"
            // todo ďaľšie colorý
            // todo a aby nula bola takz niejaky prazdny color
    };

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
            return handleInput(); // fixme rekurzia - hluposť
        }
    }

    private void printField() {
        Tile[][] fieldArray = field.getFieldArray();
        int columns = fieldArray[0].length;
        System.out.println("-".repeat(columns * 5 + 1));
        for (Tile[] row : fieldArray) {
            for (Tile tile : row) {
                int number = tile.getPiece();
                String color = getColorForNumber(number);
                System.out.printf("| %s%2d%s ", color, number, RESET);
            }
            System.out.println("|");
            System.out.println("-".repeat(columns * 5 + 1));
        }
    }

    private String getColorForNumber(int num) {
        if (num == 0) return COLORS[0];
        return COLORS[num % COLORS.length];
    }

    public static void closeScanner() {
        scanner.close();
    }

}

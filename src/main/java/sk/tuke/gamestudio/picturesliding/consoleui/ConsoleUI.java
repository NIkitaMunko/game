package sk.tuke.gamestudio.picturesliding.consoleui;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.picturesliding.core.Direction;
import sk.tuke.gamestudio.picturesliding.core.GameField;
import sk.tuke.gamestudio.picturesliding.core.GameState;
import sk.tuke.gamestudio.picturesliding.core.Tile;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.Scanner;

public class ConsoleUI {

    private final GameField field;
    private static final Scanner scanner = new Scanner(System.in);

    private static final String RESET = "\033[0m";
    private static final String GRAY = "\033[90m";
    private static final String[] COLORS = {
            "\033[32m",
            "\033[33m",
            "\033[34m",
            "\033[35m",
            "\033[36m",
            "\033[91m",
            "\033[92m",
            "\033[93m",
            "\033[94m",
            "\033[95m",
            "\033[96m",
            "\033[97m",
            "\033[48;5;250m",
            "\033[48;5;51m",
            "\033[48;5;10m",
            "\033[48;5;196m",
            "\033[48;5;82m",
            "\033[48;5;220m",
            "\033[48;5;214m",
            "\033[48;5;33m",
            "\033[48;5;124m",
            "\033[48;5;76m",
            "\033[48;5;28m",
            "\033[48;5;167m",
            "\033[48;5;5m",
            "\033[48;5;13m",
            "\033[48;5;9m",
            "\033[48;5;27m",
            "\033[48;5;17m",
            "\033[48;5;101m",
            "\033[48;5;60m",
            "\033[48;5;98m",
            "\033[48;5;131m",
            "\033[48;5;53m",
            "\033[48;5;87m",
            "\033[48;5;130m",
            "\033[48;5;210m",
            "\033[48;5;238m",
            "\033[48;5;197m",
            "\033[48;5;143m",
            "\033[48;5;202m",
            "\033[48;5;155m",
            "\033[48;5;48m",
            "\033[48;5;111m",
            "\033[48;5;105m",
            "\033[48;5;24m",
            "\033[48;5;40m",
            "\033[48;5;147m",
            "\033[48;5;67m",
            "\033[48;5;171m",
            "\033[48;5;75m",
            "\033[48;5;61m",
            "\033[48;5;77m",
            "\033[48;5;109m",
            "\033[48;5;155m",
            "\033[48;5;226m",
            "\033[48;5;92m"
    };

    @Setter
    @Autowired
    private ScoreService scoreService;
    @Setter
    @Autowired
    private CommentService commentService;
    @Setter
    @Autowired
    private RatingService ratingService;

    public ConsoleUI(GameField field) {
        this.field = field;
    }

    public void play() {
        while (!field.isSolved() && field.getState() == GameState.PLAYING) {
            printField();
            Direction direction = handleInput();
            if (direction != null) field.moveTile(direction);
        }
        printField();
        System.out.println("Congratulations! You solved the puzzle.");
//        scoreService.addScore(new Score("picture_sliding", "Nikita Munko", 228, new Date()));
    }

    private Direction handleInput() {
        System.out.print("Enter direction (WASD): ");
        String input = scanner.nextLine().trim().toLowerCase();
        Direction direction = Direction.getDirection(input);

        if (direction != null) {
            System.out.println("Player moved: " + direction);
            return direction;
        }

        System.out.println("Invalid input. Please enter W, A, S, or D.");
        return null;
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
        if (num == 0) return GRAY;
        return COLORS[(num - 1) % COLORS.length];
    }

    public static void closeScanner() {
        scanner.close();
    }

}

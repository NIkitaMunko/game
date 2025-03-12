package picturesliding.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameField {

    private GameState state;
    private final int columns;
    private final int rows;
    private Tile[][] fieldArray;

    public GameField(int rows, int columns) {
        this.columns = columns;
        this.rows = rows;
        this.state = GameState.PLAYING;

        generate();
    }


    private void generate() {
        generateField();
        fillWithTiles();
    }

    private void generateField() {
        fieldArray = new Tile[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                fieldArray[row][col] = new Tile(0);
            }
        }
    }

    private void fillWithTiles() {
        List<Integer> numbers = new ArrayList<>();
        for (int tileNumber = 1; tileNumber < rows * columns; tileNumber++) {
            numbers.add(tileNumber);
        }
        numbers.add(0);

        do {
            Collections.shuffle(numbers);
        } while (!isSolvable(numbers));

        int index = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                fieldArray[row][col] = new Tile(numbers.get(index++));
            }
        }
    }

    private boolean isSolvable(List<Integer> numbers) {
        int inversions = 0;
        int zeroRow = -1;
        int numbersSize = numbers.size();

        for (int currentIndex = 0; currentIndex < numbersSize; currentIndex++) {
            if (numbers.get(currentIndex) == 0) {
                zeroRow = (currentIndex / columns) + 1;
                continue;
            }
            for (int nextIndex = currentIndex + 1; nextIndex < numbersSize; nextIndex++)
                if (numbers.get(nextIndex) != 0 && numbers.get(currentIndex) > numbers.get(nextIndex))
                    inversions++;
        }

        return columns % 2 == 1 ? inversions % 2 == 0 : (inversions + zeroRow) % 2 == 0;
    }


    public void moveTile(Direction direction) {
        int zeroRow = -1, zeroCol = -1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (fieldArray[row][col].isEmpty()) {
                    zeroRow = row;
                    zeroCol = col;
                    break;
                }
            }
        }

        int[] move = direction.getMove();
        int newRow = zeroRow + move[0];
        int newCol = zeroCol + move[1];

        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < columns) {
            fieldArray[zeroRow][zeroCol] = new Tile(fieldArray[newRow][newCol].getPiece());
            fieldArray[newRow][newCol] = new Tile(0);
        }
    }


    public Tile[][] getFieldArray() {
        return fieldArray;
    }

    public boolean isSolved() {
        int expectedValue = 1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (row == rows - 1 && col == columns - 1)
                    return fieldArray[row][col].isEmpty();
                if (fieldArray[row][col].getPiece() != expectedValue)
                    return false;
                expectedValue++;
            }
        }

        state = GameState.SOLVED;
        return true;
    }

    public GameState getState() {
        return this.state;
    }

}

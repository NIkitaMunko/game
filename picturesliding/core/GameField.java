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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                fieldArray[i][j] = new Tile(0);
            }
        }
    }

    private void fillWithTiles() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i < rows * columns; i++) {
            numbers.add(i);
        }
        numbers.add(0);

        do {
            Collections.shuffle(numbers);
        } while (!isSolvable(numbers));

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                fieldArray[i][j] = new Tile(numbers.get(index++));
            }
        }
    }

    private boolean isSolvable(List<Integer> numbers) {
        int inversions = 0;
        int zeroRow = -1;

        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i) == 0) {
                zeroRow = (i / columns) + 1;
                continue;
            }
            for (int j = i + 1; j < numbers.size(); j++)
                if (numbers.get(j) != 0 && numbers.get(i) > numbers.get(j))
                    inversions++;
        }

        return columns % 2 == 1 ? inversions % 2 == 0 : (inversions + zeroRow) % 2 == 0;
    }


    public void moveTile(int num) { // todo переделать чтобы вводилось не число а направление (вверх-вниз-влево-вправо)
        int numRow = -1, numCol = -1, zeroRow = -1, zeroCol = -1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (fieldArray[row][col].getPiece() == num) {
                    numRow = row;
                    numCol = col;
                } else if (fieldArray[row][col].isEmpty()) {
                    zeroRow = row;
                    zeroCol = col;
                }
            }
        }

        if (numRow == -1 || numCol == -1 || zeroRow == -1 || zeroCol == -1) {
            return;
        }

        if ((Math.abs(numRow - zeroRow) == 1 && numCol == zeroCol) || (Math.abs(numCol - zeroCol) == 1 && numRow == zeroRow)) {
            fieldArray[zeroRow][zeroCol] = new Tile(num);
            fieldArray[numRow][numCol] = new Tile(0);
        }
    }


    public Tile[][] getFieldArray() {
        return fieldArray;
    }

    public boolean isSolved() {
        int expectedValue = 1;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == rows - 1 && j == columns - 1)
                    return fieldArray[i][j].isEmpty();
                if (fieldArray[i][j].getPiece() != expectedValue)
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

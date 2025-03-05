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
        // fixme generuje nie vyhratielnu hru = hluposť
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
        Collections.shuffle(numbers);

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                fieldArray[i][j] = new Tile(numbers.get(index++));
            }
        }
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

package battleship;

//import java.util.List;
import java.util.Scanner;

public class Board {
    private final int size;
    private Square[][] matrix;
//    private List<Ship> ships;

    public Board(int size) {
        this.size = size;
        createBoard();
    }

    public int getSize() {
        return size;
    }

    public Square[][] getMatrix() {
        return matrix;
    }

    public Square getSquare(int x, int y) {
        return matrix[x][y];
    }

    public char getCharAtSquare(int row, int column) {
        return getSquare(row, column).getCharacter();
    }

    public void createBoard() {
        matrix = new Square[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                matrix[row][column] = new Square(row, column, SquareStatus.OCEAN);
            }
        }
    }

}
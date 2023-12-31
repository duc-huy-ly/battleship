package battleship;

//import java.util.List;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size;
    private Square[][] matrix;
    private final List<Ship> ships;
    private int remainingShips;

    public int getRemainingShips() {
        return remainingShips;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public Board(int size) {
        this.size = size;
        createBoard();
        this.ships = new ArrayList<>();
        this.remainingShips = 0;
    }

    public int getSize() {
        return size;
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
    public void displayBoard() {
        int size = getSize();
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < size; i++) {
            System.out.printf("%c ", (char) (i + 65));
            for (int j = 0; j < size; j++) {
                System.out.printf("%c ", getCharAtSquare(i,j));
            }
            System.out.print("\n");
        }
    }

    public void addShip(Ship ship) {
        ships.add(ship);
        remainingShips++;
    }

    public void decrementShip() {
        this.remainingShips--;
    }
}
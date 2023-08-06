package battleship;

import java.nio.charset.CoderResult;
import java.util.List;
import java.util.Scanner;

public class Board {
    private int size;
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
    public Square[][] createBoard() {
        matrix = new Square[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                matrix[row][column]= new Square(row, column, SquareStatus.OCEAN);
            }
        }
        return matrix;
    }

    public void displayBoard() {
        System.out.printf("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < size; i++) {
            System.out.printf("%c ", (char)(i+65));
            for (int j = 0; j < size; j++) {
                System.out.printf("%c ", matrix[i][j].getCharacter());
            }
            System.out.print("\n");
        }
    }

    public void placeShips() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Let's place your ships!");
        for (ShipType ship : ShipType.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getLabel(), ship.getLength());
            String input = scanner.nextLine().toUpperCase();
            try {
                String[] coordinatesStrSplit = input.split(" ");
                if (coordinatesStrSplit.length != 2) {
                    throw new IllegalArgumentException("Invalid input. Please enter 2 coordinates separated by a space (\" \").");
                }
                Coordinate firstCoordinate = Coordinate.parseCoordinate(coordinatesStrSplit[0]);
                Coordinate secondCoordinate = Coordinate.parseCoordinate(coordinatesStrSplit[1]);

                boolean isHorizontal = firstCoordinate.getRow() == secondCoordinate.getRow();
                boolean isVertical = firstCoordinate.getColumn() == secondCoordinate.getColumn();

                //Check if the ships are placed in a straight line according to the original game
                if (!isHorizontal && !isVertical) {
                    throw new IllegalArgumentException("Ships can only be placed in a straight line");
                }
                // Check if the coordinates correspond to the ship length
                int distance = calculateDistance(firstCoordinate, secondCoordinate, isHorizontal);
                if (distance != ship.length) {
                    throw new IllegalArgumentException("Invalid ship length.");
                }
                placeShip(firstCoordinate, secondCoordinate, ship);
            } catch (IllegalArgumentException e) {
                System.out.println("Error :" + e.getMessage());
                System.out.println("Please try again");
            }
        }
    }

    private boolean isInAStraightLine(Coordinate firstCoordinate, Coordinate secondCoordinate) {
        return (firstCoordinate.getRow() == secondCoordinate.getRow() ||
                firstCoordinate.getColumn() == secondCoordinate.getColumn());
    }

    private int calculateDistance(Coordinate firstCoordinate, Coordinate secondCoordinate, boolean isHorizontal) {
        if (isHorizontal) {
            return Math.abs(firstCoordinate.getColumn() - secondCoordinate.getColumn());
        } else {
            return Math.abs(firstCoordinate.getRow() - secondCoordinate.getRow());
        }
    }

    private void placeShip(Coordinate coord1, Coordinate coord2, ShipType ship) {

    }
}

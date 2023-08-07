package battleship;

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
    public void createBoard() {
        matrix = new Square[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                matrix[row][column]= new Square(row, column, SquareStatus.OCEAN);
            }
        }
    }

    public void displayBoard() {
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
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
                // Coordinates class will check if the given coordinates are in bound of the grid.
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
                // Check if there are no ships overlapping
                if (isOccupiedByShipWithNeighbors(firstCoordinate, secondCoordinate, distance, isHorizontal)) {
                    throw new IllegalArgumentException("The given coordinates are already occupied.");
                }
                placeShip(firstCoordinate, secondCoordinate, ship);
            } catch (IllegalArgumentException e) {
                System.out.println("Error :" + e.getMessage());
                System.out.println("Please try again");
            }
        }
    }

    private boolean isOccupiedByShipWithNeighbors(Coordinate firstCoordinate, Coordinate secondCoordinate, int length, boolean isHorizontal) {
        if (isHorizontal) {
            int startColumn = Math.min(firstCoordinate.getColumn(), secondCoordinate.getColumn());
            int endColumn = Math.max(firstCoordinate.getColumn(), secondCoordinate.getColumn());

            for (int row = firstCoordinate.getRow() - 1; row <= firstCoordinate.getRow() + 1; row++) {
                for (int column = startColumn - 1; column <= endColumn + 1; column++) {
                    if (isValidCoordinate(row, column)) {
                        Square square = getSquare(row, column);
                        if (square.getSquareStatus().equals(SquareStatus.SHIP)) {
                            return true;
                        }
                    }
                }
            }
        } else {
            int startRow = Math.min(firstCoordinate.getRow(), secondCoordinate.getRow());
            int endRow = Math.max(firstCoordinate.getRow(), secondCoordinate.getRow());

            for (int row = startRow - 1; row <= endRow + 1; row++) {
                for (int column = firstCoordinate.getColumn() - 1; column <= firstCoordinate.getColumn() + 1; column++) {
                    if (isValidCoordinate(row, column)) {
                        Square square = getSquare(row, column);
                        if (square.getSquareStatus().equals(SquareStatus.SHIP)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isValidCoordinate(int row, int column) {
        return row >= 0 && row < 10 && column >= 0 && column < 10;
    }


    private boolean isOccupiedByShip(Coordinate firstCoordinate, Coordinate secondCoordinate, int length, boolean isHorizontal) {
        if (isHorizontal) {
            if (firstCoordinate.getRow() > secondCoordinate.getRow()) {
                for (int i = 0; i < length; i++) {
                    if (getSquare(secondCoordinate.getRow(), secondCoordinate.getColumn() + i).getSquareStatus().equals(SquareStatus.SHIP)) {
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < length; i++) {
                    if (getSquare(firstCoordinate.getRow(), secondCoordinate.getColumn() + i).getSquareStatus().equals(SquareStatus.SHIP)) {
                        return true;
                    }
                }
            }
        } else {
            if (firstCoordinate.getColumn() > secondCoordinate.getColumn()) {
                for (int i = 0; i < length; i++) {
                    if (getSquare(secondCoordinate.getRow() + i, secondCoordinate.getColumn()).getSquareStatus().equals(SquareStatus.SHIP)) {
                        return true;
                    }
                }
            } else {
                for (int i = 0; i < length; i++) {
                    if (getSquare(firstCoordinate.getRow() + i, secondCoordinate.getColumn()).getSquareStatus().equals(SquareStatus.SHIP)) {
                        return true;
                    }
                }
            }
        }
        return false;
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

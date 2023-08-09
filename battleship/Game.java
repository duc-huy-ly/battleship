package battleship;


import java.util.Scanner;

public class Game {
    final int DEFAULT_SIZE = 10;
    private final Board board = new Board(DEFAULT_SIZE);
    private final Board fogOfWarBoard = new Board(DEFAULT_SIZE);
    public void start() {
        displayBoard(board);
        placeShips(board);
        System.out.println("The game starts!");
        displayBoard(fogOfWarBoard);
        takeAShot(board, fogOfWarBoard);
    }
    public void placeShips(Board board) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Let's place your ships!");
        for (ShipType ship : ShipType.values()) {
            boolean placed = false;
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", ship.getLabel(), ship.getLength());
            while (!placed) {
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
                    int distance = Coordinate.calculateDistance(firstCoordinate, secondCoordinate, isHorizontal);
                    if (distance != ship.length) {
                        throw new IllegalArgumentException("Invalid ship length.");
                    }
                    // Check if there are no ships overlapping nor adjacent ships
                    if (isOccupiedByShipWithNeighbors(board, firstCoordinate, secondCoordinate, isHorizontal)) {
                        throw new IllegalArgumentException("The given coordinates are already occupied.");
                    }

                    placeShip(firstCoordinate, secondCoordinate, ship, isHorizontal);
                    placed = true;
                    displayBoard(board);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error :" + e.getMessage());
                    System.out.println("Please try again");
                }
            }
        }
    }
    private void placeShip(Coordinate coord1, Coordinate coord2, ShipType ship, boolean isHorizontal) {
        //determine which coordinate to use first
        if (isHorizontal) {
            if (coord1.getColumn() < coord2.getColumn()) {
                for (int i = 0; i < ship.getLength(); i++) {
                    board.getSquare(coord1.getRow(), coord1.getColumn()+i ).setSquareStatus(SquareStatus.SHIP);
                }
            } else {
                for (int i = 0; i < ship.getLength(); i++) {
                    board.getSquare(coord2.getRow(), coord2.getColumn()+i ).setSquareStatus(SquareStatus.SHIP);

                }
            }
        } else {
            if (coord1.getRow() < coord2.getRow()) {
                for (int i = 0; i < ship.getLength(); i++) {
                    board.getSquare(coord1.getRow() +i, coord1.getColumn()).setSquareStatus(SquareStatus.SHIP);
                }
            } else {
                for (int i = 0; i < ship.getLength(); i++) {
                    board.getSquare(coord2.getRow()+i , coord2.getColumn() ).setSquareStatus(SquareStatus.SHIP);

                }
            }
        }
    }
    private boolean isOccupiedByShipWithNeighbors(Board board, Coordinate firstCoordinate, Coordinate secondCoordinate, boolean isHorizontal) {
        if (isHorizontal) {
            int startColumn = Math.min(firstCoordinate.getColumn(), secondCoordinate.getColumn());
            int endColumn = Math.max(firstCoordinate.getColumn(), secondCoordinate.getColumn());

            for (int row = firstCoordinate.getRow() - 1; row <= firstCoordinate.getRow() + 1; row++) {
                for (int column = startColumn - 1; column <= endColumn + 1; column++) {
                    if (Coordinate.isValidCoordinate(row, column)) {
                        Square square = board.getSquare(row, column);
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
                    if (Coordinate.isValidCoordinate(row, column)) {
                        Square square = board.getSquare(row, column);
                        if (square.getSquareStatus().equals(SquareStatus.SHIP)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private void displayBoard(Board board) {
        int size = board.getSize();
        System.out.print("  1 2 3 4 5 6 7 8 9 10\n");
        for (int i = 0; i < size; i++) {
            System.out.printf("%c ", (char) (i + 65));
            for (int j = 0; j < size; j++) {
                System.out.printf("%c ", board.getCharAtSquare(i,j));
            }
            System.out.print("\n");
        }
    }
    private void takeAShot(Board enemyBoard, Board blankBoard) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Take a shot!");
        boolean placed = false;
        while (!placed) {
            String input = scanner.next().toUpperCase();
            try {
                Coordinate coordinate = Coordinate.parseCoordinate(input);
                String output = "";
                switch (enemyBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).getSquareStatus()) {
                    case OCEAN -> {
                        enemyBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).setSquareStatus(SquareStatus.MISS);
                        blankBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).setSquareStatus(SquareStatus.MISS);
                        output = "You missed";
                    }
                    case SHIP -> {
                        enemyBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).setSquareStatus(SquareStatus.HIT);
                        blankBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).setSquareStatus(SquareStatus.HIT);
                        output = "You hit a ship!";
                    }
                    case HIT -> output = "You already hit a target there";
                }
                displayBoard(blankBoard);
                System.out.println(output);
                displayBoard(enemyBoard);
                placed = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error :" + e.getMessage());
                System.out.println("Try again");
            }
        }
    }
}

package battleship;


import java.util.Scanner;

public class Game {
    final int DEFAULT_SIZE = 10;
    private final Board board = new Board(DEFAULT_SIZE);
    private final Board fogOfWarBoard = new Board(DEFAULT_SIZE);
    private final Scanner scanner = new Scanner(System.in);
    public void start() {
        displayBoard(board);
        placeShips(board);
        System.out.println("The game starts!");
        displayBoard(fogOfWarBoard);
        boolean sankAllShips = false;
        while (!sankAllShips) {
            takeAShot(board, fogOfWarBoard);
            if (board.getRemainingShips()==0) {
                sankAllShips = true;
            }
        }
        System.out.println("You sank the last ship. You won. Congratulations");
    }

    public void placeShips(Board board) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Let's place your ships!");
        for (ShipType shipType : ShipType.values()) {
            boolean placed = false;
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", shipType.getLabel(), shipType.getLength());
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
                    if (distance != shipType.length) {
                        throw new IllegalArgumentException("Invalid ship length.");
                    }
                    // Check if there are no ships overlapping nor adjacent ships
                    if (isOccupiedByShipWithNeighbors(board, firstCoordinate, secondCoordinate, isHorizontal)) {
                        throw new IllegalArgumentException("The given coordinates are already occupied.");
                    }

                    placeShip(firstCoordinate, secondCoordinate, shipType, isHorizontal);
                    board.addShip(new Ship(shipType));
                    placed = true;
                    displayBoard(board);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error :" + e.getMessage());
                    System.out.println("Please try again");
                }
            }
        }
    }
    private void placeShip(Coordinate coord1, Coordinate coord2, ShipType shipType, boolean isHorizontal) {
        //determine which coordinate to use first
        if (isHorizontal) {
            if (coord1.getColumn() < coord2.getColumn()) {
                for (int i = 0; i < shipType.getLength(); i++) {
                    board.getSquare(coord1.getRow(), coord1.getColumn()+i ).setSquareStatus(SquareStatus.SHIP);
                    board.getSquare(coord1.getRow(), coord1.getColumn()+i ).setShipType(shipType);
                }
            } else {
                for (int i = 0; i < shipType.getLength(); i++) {
                    board.getSquare(coord2.getRow(), coord2.getColumn()+i ).setSquareStatus(SquareStatus.SHIP);
                    board.getSquare(coord2.getRow(), coord2.getColumn()+i ).setShipType(shipType);
                }
            }
        } else {
            if (coord1.getRow() < coord2.getRow()) {
                for (int i = 0; i < shipType.getLength(); i++) {
                    board.getSquare(coord1.getRow() +i, coord1.getColumn()).setSquareStatus(SquareStatus.SHIP);
                    board.getSquare(coord1.getRow() + i, coord1.getColumn() ).setShipType(shipType);
                }
            } else {
                for (int i = 0; i < shipType.getLength(); i++) {
                    board.getSquare(coord2.getRow()+i , coord2.getColumn() ).setSquareStatus(SquareStatus.SHIP);
                    board.getSquare(coord2.getRow() + i, coord2.getColumn()).setShipType(shipType);
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
        System.out.println("Take a shot!");
        boolean placed = false;
        while (!placed) {
            String input = scanner.next().toUpperCase();
            try {
                Coordinate coordinate = Coordinate.parseCoordinate(input);
                String output = "";
                switch (enemyBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).getSquareStatus()) {
                    case OCEAN, MISS -> {
                        enemyBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).setSquareStatus(SquareStatus.MISS);
                        blankBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).setSquareStatus(SquareStatus.MISS);
                        output = "You missed";
                    }
                    case SHIP, HIT-> {
                        enemyBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).setSquareStatus(SquareStatus.HIT);
                        blankBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).setSquareStatus(SquareStatus.HIT);
                        decrementHPOfTheHitShip(coordinate, enemyBoard);
                        if (shipIsSunk(coordinate, enemyBoard)) {
                            output = "You sank a ship ! specify a new target:";
                            enemyBoard.decrementShip();
                        } else {
                            output = "You hit a ship!";
                        }

                    }
                }
                displayBoard(blankBoard);
                System.out.println(output);
                placed = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error :" + e.getMessage());
                System.out.println("Try again");
            }
        }
    }

    private void decrementHPOfTheHitShip(Coordinate coordinate, Board enemyBoard) {
        ShipType typeOfShipAtCoordinate = enemyBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).getShipType();
        //get the ship object with the matching shiptype
        for ( Ship ship : board.getShips()) {
            if (ship.getShipType().equals(typeOfShipAtCoordinate)) {
                ship.decrementShipHP();
            }
        }
    }

    private boolean shipIsSunk(Coordinate coordinate, Board enemyBoard) {
        //find the ship at the given coordinate
        //get the shiptype from the given coordinate
        ShipType typeOfShipAtCoordinate = enemyBoard.getSquare(coordinate.getRow(), coordinate.getColumn()).getShipType();
        //get the ship object with the matching shiptype
        for ( Ship ship : board.getShips()) {
            if (ship.getShipType().equals(typeOfShipAtCoordinate)) {
                //if that ship's hp is 0, return true
                if (ship.getShipHP() == 0) {
                    return true;
                }
            }
        }
        //else return false
        return false;
    }
}

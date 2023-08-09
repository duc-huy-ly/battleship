package battleship;

public class Square {
    private final int row;
    private final int column;
    private SquareStatus squareStatus;
    private ShipType shipType;
    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType ship) {
        this.shipType = ship;
    }

    public void setSquareStatus(SquareStatus squareStatus) {
        this.squareStatus = squareStatus;
    }

    public Square(int row, int column, SquareStatus squareStatus) {
        this.row = row;
        this.column = column;
        this.squareStatus = squareStatus;
    }

    public SquareStatus getSquareStatus() {
        return squareStatus;
    }

    public char getCharacter() {
        char result = ' ';
        switch (squareStatus) {
            case OCEAN -> result = '~';
            case SHIP -> result = 'O';
            case HIT -> result = 'X';
            case MISS -> result = 'M';
        }
        return result;
    }
}

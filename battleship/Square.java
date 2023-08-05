package battleship;

public class Square {
    private int row;
    private int column;
    private SquareStatus squareStatus;

    public Square(int row, int column, SquareStatus squareStatus) {
        this.row = row;
        this.column = column;
        this.squareStatus = squareStatus;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public SquareStatus getSquareStatus() {
        return squareStatus;
    }

    public char getCharacter() {
        char result = ' ';
        switch (squareStatus) {
            case OCEAN -> {
                result = '~';
            }
            case SHIP -> {
                result = 'O';
            }
            case HIT -> {
                result = 'H';
            }
            case MISS -> {
                result = 'M';
            }
        }
        return result;
    }
}

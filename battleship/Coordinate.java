package battleship;

public class Coordinate {
    private int row;
    private int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    // Helper method to parse the coordinate string (e.g., "A1", "B5") and create a Coordinate object
    public static Coordinate parseCoordinate(String input) {
        if (input.length() < 2) {
            throw new IllegalArgumentException("Invalid coordinate format");
        }
        char colChar = Character.toUpperCase(input.charAt(0));
        int row = Integer.parseInt(input.substring(1));
        if (colChar < 'A' || colChar > 'J' || row < 1 || row > 10) {
            throw new IllegalArgumentException("Invalid coordinate values. Try again.");
        }
        int col = colChar - 'A';
        row--; //convert row number to zero based index
        return new Coordinate(row, col);
    }
}

package battleship;

public class Board {
    private int size;
    private Square[][] matrix;

    public Board(int size) {
        this.size = size;
        this.matrix = new Square[size][size];
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
        System.out.println();
    }
}

package battleship;

public class Game {
    private String[][] grid;
    private final int dimension = 11;

    public Game() {
        this.grid = new String[11][11];
        initialiseEmptyGrid();

    }

    public void start() {
        printGrid();
    }
    private void printGrid() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.printf("%s ", grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void initialiseEmptyGrid() {
        //set the cell [0][0] containing the " " character
        grid[0][0] = " ";
        //set the first row and column
        for (int i = 1; i < dimension; i++) {
            grid[0][i] = Integer.toString(i);
        }
        for (int i = 0; i < dimension - 1; i++) {
            grid[i+1][0] = Character.toString( (char)(i+65));
        }
        // The rest of the grid, fill with "~"
        for (int i = 1; i < dimension; i++) {
            for (int j = 1; j < dimension; j++) {
                grid[i][j]="~";
            }
        }

    }
}

package battleship;

public class Game {
    private final Cell[][] grid;
    private final int dimension = 11;

    public Game() {
        this.grid = new Cell[dimension][dimension];
        initialiseEmptyGrid();

    }

    public void start() {
        printGrid();
    }
    private void printGrid() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.printf("%s ", grid[i][j].getContent());
            }
            System.out.println();
        }
        System.out.println();
    }

    private void initialiseEmptyGrid() {
        //set the cell [0][0] containing the " " character
        grid[0][0] = new Cell(" ", CellState.label);
        //set the first row and column
        for (int i = 1; i < dimension; i++) {
            grid[0][i] = new Cell(Integer.toString(i), CellState.label);
        }
        for (int i = 0; i < dimension - 1; i++) {
            grid[i+1][0] = new Cell(Character.toString( (char)(i+65)), CellState.label);
        }
        // The rest of the grid, fill with "~"
        for (int i = 1; i < dimension; i++) {
            for (int j = 1; j < dimension; j++) {
                grid[i][j]=new Cell("~", CellState.fog);
            }
        }

    }
}

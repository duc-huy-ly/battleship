package battleship;


public class Game {
    public void start() {
        Board board = new Board(10);
        board.displayBoard();
        board.placeShips();
    }
}

package battleship;


public class Game {
    private Board board = new Board(10);
    public void start() {
        board.displayBoard();
        board.placeShips();
        board.takeAShot();
    }
}

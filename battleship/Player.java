package battleship;

public class Player {
    private final String label;
    private final Board board;
    private final Board blankBoard;

    public Player(String label) {
        this.label = label;
        this.board = new Board(10);
        this.blankBoard = new Board(10);
    }

    public String getLabel() {
        return label;
    }

    public Board getBoard() {
        return board;
    }

    public Board getBlankBoard() {
        return blankBoard;
    }
}

package battleship;

public class Cell {
    private CellState State;
    private String content;

    public CellState getState() {
        return State;
    }

    public void setState(CellState state) {
        State = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Cell(String content, CellState state) {
        this.content = content;
        this.State = state;
    }

}

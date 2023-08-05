package battleship;

public enum ShipType {
    CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser",3),
    DESTROYER("Destroyer",2);

    public final String label;
    public final int length;


    public int getLength() {
        return length;
    }

    public String getLabel() {
        return label;
    }


    ShipType(String label, int length) {
        this.label = label;
        this.length = length;
    }
}

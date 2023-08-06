package battleship;

import java.util.List;

public class Ship {
    private List<Square> NewShip;
    private ShipType shipType;

    public Ship(List<Square> newShip, ShipType shipType) {
        NewShip = newShip;
        this.shipType = shipType;
    }
    public List<Square> getFields() {
        return NewShip;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void add(Square square) {
        NewShip.add(square);
    }
}

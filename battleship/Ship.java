package battleship;

public class Ship {
    private final ShipType shipType;
    private int ShipHP;

    public int getShipHP() {
        return ShipHP;
    }


    public Ship(ShipType shipType) {
        this.shipType = shipType;
        this.ShipHP = shipType.getLength();
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void decrementShipHP() {
        this.ShipHP--;
    }

}

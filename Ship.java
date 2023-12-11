public class Ship {
    public Tile[] location;
    private boolean alive = true;
    public String shipName;
    public int length;
    public boolean rotate;

    public Ship (int length, Tile[] location, String name, boolean rotate) {
        this.location = location;
        this.alive = true;
        this.shipName = name;
        this.length = length;
        this.rotate = rotate;
        for (Tile a: location) {
            a.setShip(this);
        }
    }

    public boolean isDead () {
        for (Tile a: location) {
            if (!a.beenHit()) {
                return !alive;
            }
        }
        sink();
        return !alive;
    }

    public void sink () {
        this.alive = false;
    }
}
public class Ship {
    public Tile[] location;
    private boolean alive = true;
    public String shipName;

    public Ship (int length, Tile[] location, String name) {
        this.location = location;
        this.alive = true;
        this.shipName = name;
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
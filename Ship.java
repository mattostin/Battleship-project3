public class Ship {
    public Tile[] location;
    private boolean alive = true;

    public Ship (int length, Tile[] location) {
        this.location = location;
        this.alive = true;
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
public class Tile {
    private int y;
    private int x;
    private boolean hasBoat;
    private boolean hit;

    public Tile (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void placeBoat () {
        this.hasBoat = true;
    }

    public void hit () {
        this.hit = true;
    }

    public boolean hasBoat () {
        return hasBoat;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

    public boolean alreadyHit () {
        return hit;
    }
}

public class Tile {
    private int y;
    private int x;
    private boolean hasBoat;
    private boolean hit;

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

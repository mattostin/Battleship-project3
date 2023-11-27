public class Tile {
    private int y;
    private int x;
    private boolean hasBoat;
    private boolean hit;
    public String name;
    public int tileNo;

    public Tile (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void placeBoat (String name, int tileNo) {
        this.hasBoat = true;
        this.name = name;
        this.tileNo = tileNo;
    }

    public void hit () {
        this.hit = true;
    }
//is beenHit repetitive with alreadyHit?
    public boolean beenHit () {
        return hit;
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

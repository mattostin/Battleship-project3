public class Ship {
    private Tile[] location;
    private boolean[] hit;
    private boolean alive;

    public Ship (int length, Tile[] location) {
        this.location = location;
        this.alive = true;
        this.hit = new boolean[length];
        for (int i = 0; i < length; i++) {
            hit[i] = false;
        }
    }

    public boolean isDead () {
        for (boolean a: hit) {
            if (!a) {
                return false;
            }
        }
        return true;
    }

    public void sink () {
        this.alive = false;
    }

    public boolean checkHit (Tile a) {
        int count = 0;
        for (Tile b: location) {
            count++;
            if (b.equals(a)) {
                hit[count] = true;
                if (this.isDead()) 
                {
                    sink();
                }
                return true;
            }
        }
        return false;
    }


}
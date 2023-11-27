import java.util.ArrayList;
import java.util.Random;

public class HarderBot extends Bot {

    public HarderBot(boolean turn, Board ownBoard, Board oppBoard, String[] shipNames, int[] shipSizes) {
        super(turn, ownBoard, oppBoard, shipNames, shipSizes);
    }

    private Random rand = new Random();


    Thread harderbotCode = new Thread(() -> {
        placeShips();
        while (true) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {
                System.out.println("FAILURE IN HARDER BOT SLEEP");
                return;
            }
            if (this.turn) {
                //System.out.println("SUPPOSED TO SIMULATE");
                simulateMove();
            }
        }
    });


    protected Ship placeShipOnBoard(int x, int y, int size, boolean rotate) {
        Tile[] location = new Tile[size];
        for (int i = 0; i < size; i++) {
            if (rotate) {
                location[i] = OwnBoard.getTile(x + i, y);
            } else {
                location[i] = OwnBoard.getTile(x, y + i);
            }
        }
        Ship ship = new Ship(size, location);
        return ship;
    }


    protected void placeShips() {
        ArrayList<Ship> shipList = new ArrayList<>();
        for (int size : sizes) {
            Ship nextShip = placeShip(size);
            for (Tile a: nextShip.location) {
                a.placeBoat();
            }
            shipList.add(nextShip);
        }
        this.SetShips(shipList);
    }

    /*private Ship placeShipStrategically(int size) {
        while (true) {
            int x = rand.nextInt(this.OwnBoard.boardSize[0]);
            int y = rand.nextInt(this.OwnBoard.boardSize[1]);

            boolean rotate = rand.nextBoolean();

            if (isValidPlacement(x, y, size, rotate)) {
                return placeShipOnBoard(x, y, size, rotate);
            }
        }
    }
    */

    



    private int turns;
    private int hits;

    

    private boolean isValidTarget(int x, int y) {
        return x >= 0 && x < this.OwnBoard.boardSize[0] &&
               y >= 0 && y < this.OwnBoard.boardSize[1] &&
               !OppBoard.getTile(x, y).beenHit();
    }


        protected void simulateMove() {
        while (true) {

//make it so depending on which boat is hit it will make a good move 
//will need further implementation

          /*  if (hits >=0){
                if (isValidTarget(x + 1, y)) {
                    return OppBoard.getTile(x + 1, y);
                }
                if (isValidTarget(x - 1, y)) {
                    return OppBoard.getTile(x - 1, y);
                }
                if (isValidTarget(x, y + 1)) {
                    return OppBoard.getTile(x, y + 1);
                }
                if (isValidTarget(x, y - 1)) {
                    return OppBoard.getTile(x, y - 1);
                }
            }
            */
            int a = rand.nextInt(this.OwnBoard.boardSize[0]);
            int b = rand.nextInt(this.OwnBoard.boardSize[1]);
            Tile target = OppBoard.getTile(a, b);
            boolean breaker = this.fire(target);
            if (breaker) {
                if (!target.hasBoat()) {
                    turns++;
                    this.fired = true;
                    this.turn = false;
                    break;
                }
                else{
                    hits++;
                    Tile previousHit = OppBoard.getTile(a, b);
                }
            }
        }
    }


    

}



/*
 * protected void simulateMove() {
    int a, b;

    if (hits == 0) {
        a = rand.nextInt(this.OwnBoard.boardSize[0]);
        b = rand.nextInt(this.OwnBoard.boardSize[1]);
    } else {
        Tile previousHit = findPreviousHit();
        if (previousHit != null) {
            int x = previousHit.getX();
            int y = previousHit.getY();

            int[] directions = {1, -1, 0, 0};
            shuffleArray(directions);

            for (int direction : directions) {
                if (direction != 0) {
                    if (isValidTarget(x + direction, y)) {
                        a = x + direction;
                        b = y;
                        Tile target = OppBoard.getTile(a, b);
                        boolean breaker = this.fire(target);
                        if (breaker) {
                            turns++;
                            this.fired = true;
                            this.turn = false;
                            break;
                        }
                    }
                } else {
                    if (isValidTarget(x, y + direction)) {
                        a = x;
                        b = y + direction;
                        Tile target = OppBoard.getTile(a, b);
                        boolean breaker = this.fire(target);
                        if (breaker) {
                            turns++;
                            this.fired = true;
                            this.turn = false;
                            break;
                        }
                    }
                }
            }
        } else {
            a = rand.nextInt(this.OwnBoard.boardSize[0]);
            b = rand.nextInt(this.OwnBoard.boardSize[1]);
        }
    }
}

private Tile findPreviousHit() {
    for (Tile[] row : OppBoard.getBoard()) {
        for (Tile tile : row) {
            if (tile.hasBoat() && tile.beenHit()) {
                return tile;
            }
        }
    }
    return null;
}

private void shuffleArray(int[] array) {
    Random rand = new Random();
    for (int i = array.length - 1; i > 0; i--) {
        int index = rand.nextInt(i + 1);
        int temp = array[index];
        array[index] = array[i];
        array[i] = temp;
    }
}

 */

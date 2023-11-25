import java.util.Random;
import java.util.*;

public class Bot extends Player {

    private Random rand = new Random();

    public Bot(boolean turn, Board ownBoard, Board oppBoard, String[] shipNames, int[] shipSizes) {
        super(turn, ownBoard, oppBoard, shipNames, shipSizes);

        Thread botCode = new Thread(() -> {
            placeShips();
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("FAILURE IN BOT SLEEP");
                    return;
                }
                if (this.turn) {
                    //System.out.println("SUPPOSED TO SIMULATE");
                    simulateMove();
                }
            }
        });
    
        botCode.start();
    }

    protected void placeShips() {
        ArrayList<Ship> shipList = new ArrayList<>();
        for (int size : sizes) {
            shipList.add(placeShip(size));
        }
        this.SetShips(shipList);
    }

    public Ship placeShip(int size) {
        boolean rotate = rand.nextBoolean();

        while (true) {
            int x = rand.nextInt(this.OwnBoard.boardSize[0]);
            int y = rand.nextInt(this.OwnBoard.boardSize[1]);

            if (rotate) {
                if (x + size <= this.OwnBoard.boardSize[0] && isValidPlacement(x, y, size, rotate)) {
                    return placeShipOnBoard(x, y, size, rotate);
                    
                }
            } else {
                if (y + size <= this.OwnBoard.boardSize[1] && isValidPlacement(x, y, size, rotate)) {
                    return placeShipOnBoard(x, y, size, rotate);
                   
                }
            }
        }
    }

    protected boolean isValidPlacement(int x, int y, int size, boolean rotate) {
        if (rotate) {
            for (int i = 0; i < size; i++) {
                if (OwnBoard.getTile(x + i, y).hasBoat()) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (OwnBoard.getTile(x, y + i).hasBoat()) {
                    return false;
                }
            }
        }
        return true;
    }

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

    protected void simulateMove() {
        while (true) {
            int a = rand.nextInt(this.OwnBoard.boardSize[0]);
            int b = rand.nextInt(this.OwnBoard.boardSize[1]);
            Tile target = OppBoard.getTile(a, b);
            boolean breaker = this.fire(target);
            if (breaker) {
                if (!target.hasBoat()) {
                    this.fired = true;
                    this.turn = false;
                    break;
                }
            }
        }
    }

    
}

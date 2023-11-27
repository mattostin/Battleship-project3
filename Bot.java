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

    public void placeShips() {
        ArrayList<Ship> shipList = new ArrayList<>();
        for (int i = sizes.length - 1; i >= 0; i--) {
            Ship nextShip = placeShip(sizes[i], names[i]);
            int count = 1;
            for (Tile a: nextShip.location) {
                a.placeBoat(nextShip.shipName, count, nextShip.rotate);
                count++;
            }
            shipList.add(nextShip);
        }
        this.SetShips(shipList);
    }

    public Ship placeShip(int size, String name) {
        boolean rotate = rand.nextBoolean();

        while (true) {
            int x = rand.nextInt(this.OwnBoard.boardSize[0]);
            int y = rand.nextInt(this.OwnBoard.boardSize[1]);

            if (rotate) {
                if (x + size <= this.OwnBoard.boardSize[0] && isValidPlacement(x, y, size, rotate)) {
                    return placeShipOnBoard(x, y, size, rotate, name);
                    
                }
            } else {
                if (y + size <= this.OwnBoard.boardSize[1] && isValidPlacement(x, y, size, rotate)) {
                    return placeShipOnBoard(x, y, size, rotate, name);
                   
                }
            }
        }
    }

    private boolean isValidPlacement(int x, int y, int size, boolean rotate) {
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

    private Ship placeShipOnBoard(int x, int y, int size, boolean rotate, String name) {
        Tile[] location = new Tile[size];
        for (int i = 0; i < size; i++) {
            if (rotate) {
                location[i] = OwnBoard.getTile(x + i, y);
            } else {
                location[i] = OwnBoard.getTile(x, y + i);
            }
        }
        Ship ship = new Ship(size, location, name, rotate);
        return ship;
    }

    public void simulateMove() {
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

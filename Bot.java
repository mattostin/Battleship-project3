import java.util.Random;

public class Bot extends Player {

    private static final int[] bot_ship_size = {2, 3, 3, 4, 5};

    public Bot(boolean turn, Board ownBoard, Board oppBoard) {
        super(turn, ownBoard, oppBoard);
    }

    public void placeShips() {
        for (int size : bot_ship_size) {
            placeShip(size);
        }
    }

    public void placeShip(int size) {
        Random rand = new Random();
        boolean rotate = rand.nextBoolean();

        while (true) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);

            if (rotate) {
                if (x + size <= 10 && isValidPlacement(x, y, size, rotate)) {
                    placeShipOnBoard(x, y, size, rotate);
                    break;
                }
            } else {
                if (y + size <= 10 && isValidPlacement(x, y, size, rotate)) {
                    placeShipOnBoard(x, y, size, rotate);
                    break;
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

    private void placeShipOnBoard(int x, int y, int size, boolean rotate) {
        Tile[] location = new Tile[size];
        for (int i = 0; i < size; i++) {
            if (rotate) {
                location[i] = OwnBoard.getTile(x + i, y);
            } else {
                location[i] = OwnBoard.getTile(x, y + i);
            }
        }
        Ship ship = new Ship(size, location);
    }



    public void simulateMove() {
        //dane put here for some reason.
    }
}

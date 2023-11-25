import java.util.ArrayList;
import java.util.Random;

public class HarderBot extends Bot {

    public HarderBot(boolean turn, Board ownBoard, Board oppBoard, String[] shipNames, int[] shipSizes) {
        super(turn, ownBoard, oppBoard, shipNames, shipSizes);
    }

    private Random rand = new Random();


    @Override
    public void placeShips() {
        ArrayList<Ship> shipList = new ArrayList<>();

        // Place larger ships first for a more strategic placement
        for (int i = sizes.length - 1; i >= 0; i--) {
            int size = sizes[i];
            shipList.add(placeShipStrategically(size));
        }

        this.SetShips(shipList);
    }

    private Ship placeShipStrategically(int size) {
        while (true) {
            int x = rand.nextInt(this.OwnBoard.boardSize[0]);
            int y = rand.nextInt(this.OwnBoard.boardSize[1]);

            boolean rotate = rand.nextBoolean();

            if (isValidPlacement(x, y, size, rotate)) {
                return placeShipOnBoard(x, y, size, rotate);
            }
        }
    }

    // Override other methods or add new methods for more strategic gameplay
    

    @Override
    public void simulateMove() {
        if (!turn) {
            // Implement a strategic firing strategy here
            Tile target = bestMove();
            boolean breaker = this.fire(target);

            if (breaker && !target.hasBoat()) {
                this.fired = true;
                this.turn = false;
            }
        }
    }

    private int turns;
    private int hits;

    
    public ArrayList<Tile> getHitTiles(){
        ArrayList<Tile> hitTiles = new ArrayList<>();
        for (int r = 0; r < this.OppBoard.boardSize[0]; r++){
            for (int c = 0; c < this.OppBoard.boardSize[1]; c++){
                if (this.OppBoard[r][c].){
                    hitTiles.add(this.OppBoard[r][c]);
                }

            }
        }
    }

    private boolean isValidTarget(int x, int y) {
        // Check if the target coordinates are within the board boundaries and haven't been fired at
        return x >= 0 && x < this.OwnBoard.boardSize[0] &&
               y >= 0 && y < this.OwnBoard.boardSize[1] &&
               !OppBoard.getTile(x, y).beenHit();
    }


    private Tile bestMoveMaker() {
        // Get the hit tiles from the opponent's board
        ArrayList<Tile> hitTiles = OppBoard.getHitTiles();
    
        // Iterate over the hit tiles and try firing around each hit tile
        for (Tile hitTile : hitTiles) {
            int x = hitTile.getX();
            int y = hitTile.getY();
    
            // Try firing in all four directions around the hit tile
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
    
        // If no strategic target found, choose a random target
        int a = rand.nextInt(this.OwnBoard.boardSize[0]);
        int b = rand.nextInt(this.OwnBoard.boardSize[1]);
        return OppBoard.getTile(a, b);
    }
    

    // Add any additional methods or strategies as needed
}

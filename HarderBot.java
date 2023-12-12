
import java.util.ArrayList;
import java.util.Random;

public class HarderBot extends Bot {

    private Random rand = new Random();

    public HarderBot(boolean turn, Board ownBoard, Board oppBoard, String[] shipNames, int[] shipSizes) {
        super(turn, ownBoard, oppBoard, shipNames, shipSizes);

        Thread harderbotCode = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("FAILURE IN HARDER BOT SLEEP");
                    return;
                }
                if (this.turn) {
                    simulateMove();
                    //debugging purposes
                    System.out.println("Harder Bot Shoots");
                }
            }
        });

        harderbotCode.start();
    }

    @Override
    public void simulateMove() {
        if (turn) {
            Tile target = bestMove();
            boolean breaker = this.fire(target);

            if (breaker && !target.hasBoat()) {
                this.fired = true;
                this.turn = false;
            }
        }
    }

    private Tile bestMove() {
        ArrayList<Tile> hitTiles = getHitTiles();
        ArrayList<Tile> possibleMoves = new ArrayList<>();

        for (Tile hitTile : hitTiles) {
            int x = hitTile.getX();
            int y = hitTile.getY();

            addValidTarget(possibleMoves, x + 1, y);
            addValidTarget(possibleMoves, x - 1, y);
            addValidTarget(possibleMoves, x, y + 1);
            addValidTarget(possibleMoves, x, y - 1);
        }

        if (!possibleMoves.isEmpty()) {
            int randomIndex = rand.nextInt(possibleMoves.size());
            return possibleMoves.get(randomIndex);
        }

        int a = rand.nextInt(this.OwnBoard.boardSize[0]);
        int b = rand.nextInt(this.OwnBoard.boardSize[1]);
        return OppBoard.getTile(a, b);
    }

    private void addValidTarget(ArrayList<Tile> possibleMoves, int x, int y) {
        if (isValidTarget(x, y)) {
            possibleMoves.add(OppBoard.getTile(x, y));
        }
    }

    private ArrayList<Tile> getHitTiles() {
        ArrayList<Tile> hitTiles = new ArrayList<>();

        for (int r = 0; r < this.OppBoard.boardSize[0]; r++) {
            for (int c = 0; c < this.OppBoard.boardSize[1]; c++) {
                Tile tile = OppBoard.getTile(r, c);
                if (tile.hasBoat() && tile.beenHit() && !tile.ship.isDead()) {
                    hitTiles.add(tile);
                }
            }
        }
        return hitTiles;
    }

    private boolean isValidTarget(int x, int y) {
        return x >= 0 && x < this.OwnBoard.boardSize[0] &&
                y >= 0 && y < this.OwnBoard.boardSize[1] &&
                !OppBoard.getTile(x, y).beenHit();
    }
}

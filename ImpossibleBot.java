
// import java.util.ArrayList;
// import java.util.Random;

// public class ImpossibleBot extends HarderBot {

//     private Random rand = new Random();
//     private int turns;
//     private int hits;

//     public ImpossibleBot(boolean turn, Board ownBoard, Board oppBoard, String[] shipNames, int[] shipSizes) {
//         super(turn, ownBoard, oppBoard, shipNames, shipSizes);

//         Thread impossiblebotCode = new Thread(() -> {
//             placeShips();
//             while (true) {
//                 try {
//                     Thread.sleep(1);
//                 } catch (Exception e) {
//                     System.out.println("FAILURE IN HARDER BOT SLEEP");
//                     return;
//                 }
//                 if (this.turn) {
//                     simulateMove();
//                 }
//             }
//         });

//         impossiblebotCode.start();
//     }

//     public void placeShips() {
//         ArrayList<Ship> shipList = new ArrayList<>();
//         for (int i = sizes.length - 1; i >= 0; i--) {
//             Ship nextShip = placeShip(sizes[i], names[i]);
//             int count = 1;
//             for (Tile a: nextShip.location) {
//                 a.placeBoat(nextShip.shipName, count, nextShip.rotate);
//                 count++;
//             }
//             shipList.add(nextShip);
//         }
//         this.SetShips(shipList);
//     }
//     //Don't need its battleship don't need to place strategically
//      /*private Ship placeShipStrategically(int size) {
//         while (true) {
//             int x = rand.nextInt(this.OwnBoard.boardSize[0]);
//             int y = rand.nextInt(this.OwnBoard.boardSize[1]);
//             boolean rotate = rand.nextBoolean();

//             if (isValidPlacement(x, y, size, rotate)) {
//                 return placeShipOnBoard(x, y, size, rotate);
//             }
//         }
//     }
//     */

//     public boolean isValidPlacement(int x, int y, int size, boolean rotate) {
//         if (rotate) {
//             for (int i = 0; i < size; i++) {
//                 if (OwnBoard.getTile(x + i, y).hasBoat()) {
//                     return false;
//                 }
//             }
//         } else {
//             for (int i = 0; i < size; i++) {
//                 if (OwnBoard.getTile(x, y + i).hasBoat()) {
//                     return false;
//                 }
//             }
//         }
//         return true;
//     }

//     private Ship placeShipOnBoard(int x, int y, int size, boolean rotate, String name) {
//         Tile[] location = new Tile[size];
//         for (int i = 0; i < size; i++) {
//             if (rotate) {
//                 location[i] = OwnBoard.getTile(x + i, y);
//             } else {
//                 location[i] = OwnBoard.getTile(x, y + i);
//             }
//         }
//         Ship ship = new Ship(size, location, name, rotate);
//         return ship;
//     }

//     @Override
//     public void simulateMove() {
//         int i = 0;
//         if (!turn) {
//             //checks if turn then if its not goes 10 times ina  row
//             //may or may not work.
//             for (int i = 0; i < 10; i++){
//                 Tile target = bestMove();
//                 boolean breaker = this.fire(target);
//             }

//             if (breaker && !target.hasBoat()) {
//                 this.fired = true;
//                 this.turn = false;
//             }
//         }
//     }

//     private Tile bestMove() {
//         ArrayList<Tile> hitTiles = getHitTiles();

//         for (Tile hitTile : hitTiles) {
//             int x = hitTile.getX();
//             int y = hitTile.getY();

//             if (isValidTarget(x + 1, y)) {
//                 return OppBoard.getTile(x + 1, y);
//             }
//             if (isValidTarget(x - 1, y)) {
//                 return OppBoard.getTile(x - 1, y);
//             }
//             if (isValidTarget(x, y + 1)) {
//                 return OppBoard.getTile(x, y + 1);
//             }
//             if (isValidTarget(x, y - 1)) {
//                 return OppBoard.getTile(x, y - 1);
//             }
//         }

//         int a = rand.nextInt(this.OwnBoard.boardSize[0]);
//         int b = rand.nextInt(this.OwnBoard.boardSize[1]);
//         return OppBoard.getTile(a, b);
//     }

//     private ArrayList<Tile> getHitTiles() {
//         ArrayList<Tile> hitTiles = new ArrayList<>();
//         for (int r = 0; r < this.OppBoard.boardSize[0]; r++) {
//             for (int c = 0; c < this.OppBoard.boardSize[1]; c++) {
//                 Tile tile = OppBoard.getTile(r, c);
//                 if (tile.hasBoat() && tile.beenHit()) {
//                     hitTiles.add(tile);
//                 }
//             }
//         }
//         return hitTiles;
//     }

//     private boolean isValidTarget(int x, int y) {
//         return x >= 0 && x < this.OwnBoard.boardSize[0] &&
//                 y >= 0 && y < this.OwnBoard.boardSize[1] &&
//                 !OppBoard.getTile(x, y).beenHit();
//     }
// }



import java.util.Random;
import java.util.*;

public class ImpossibleBot extends HarderBot {

    private Random rand = new Random();

    public ImpossibleBot(boolean turn, Board ownBoard, Board oppBoard, String[] shipNames, int[] shipSizes) {
        super(turn, ownBoard, oppBoard, shipNames, shipSizes);

        Thread impossiblebotCode = new Thread(() -> {
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
    
        impossiblebotCode.start();
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
            for (int i = 0; i < 10; i++){
                int a = rand.nextInt(this.OwnBoard.boardSize[0]);
                int b = rand.nextInt(this.OwnBoard.boardSize[1]);
                Tile target = OppBoard.getTile(a, b);
                //boolean breaker = this.fire(target);
                if (target.hasBoat()){
                    i--;
                }
            }
                // if (breaker) {
                //     if (!target.hasBoat()) {
            this.fired = true;
            this.turn = false;
            break;
        }
    }
            
}

    

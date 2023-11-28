import java.util.*;

public class Board {
    public Tile[][] tiles;
    public ArrayList<Ship> ships;
    public int[] boardSize;
    public boolean shipsSet;

    public Board (int[] boardsize) {
        this.boardSize = boardsize;
        tiles = new Tile[boardSize[0]][boardSize[1]];    
        for (int i = 0; i < boardSize[0]; i++) {
            for (int j = 0; j < boardSize[1]; j++) {
                tiles[i][j] = new Tile (i, j);
            }
        }
    }

    public void SetShips (ArrayList<Ship> ships) {
        this.ships = ships;
        for (Ship a: ships) {
            for (int i = 0; i < a.location.length; i++) {
                tiles[a.location[i].getX()][a.location[i].getY()].placeBoat(a.shipName, a.length - i, a.rotate);
            }
        }
        shipsSet = true;      
    }

    public Tile getTile (int x, int y) {
        return tiles[x][y];
    } 

    public void hit (int x, int y) {
        tiles[x][y].hit();
    } 

    public boolean gameOver () {
        for (Ship a: ships) {
            if (!a.isDead()) {
                return false;
            }
        }
        return true;
    }
}

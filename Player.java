import java.util.*;

public class Player {
    public Board OwnBoard;
    public Board OppBoard;
    public boolean turn;
    public boolean shipsSet;
    public boolean fired;
    public ArrayList<Tile> firedAt;
    public boolean allSunk;
    public String[] names;
    public int[] sizes;


    public Player (boolean turn, Board own, Board opp, String[] shipNames, int[] shipSizes) {
        firedAt = new ArrayList<>();
        this.turn = turn;
        OwnBoard = own;
        OppBoard = opp;
        this.allSunk = false;
        this.sizes = shipSizes;
        this.names = shipNames;

    }

    public Player () {
        this.allSunk = false;
    }

    public void SetShips (ArrayList<Ship> ships) {
        shipsSet = true;
        OwnBoard.SetShips (ships);
    }
    
    public boolean isValidPlacement(int x, int y, int size, boolean rotate) {
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

    public boolean fire (Tile tile) {
        if (!(tile.beenHit()) && this.turn) {
            tile.hit();
            this.firedAt.add(tile);
            allSunk = OppBoard.gameOver();
            //System.out.println(tile.getX() + " " + tile.getY() + " " + allSunk + " " + OppBoard.gameOver());
            return true;
        }
        else {
            return false;
        }
    }

    public void simulateMove(Tile currentTile){

        if (firedAt.contains(currentTile)){
            //need to figure out this implementation
        }
        else if (!(currentTile.beenHit()) && !turn){
            currentTile.hit();
            firedAt.add(currentTile);

            if (currentTile.hasBoat()){
                //need to add implementation here.

                //this is placeholder

                System.out.println("Hit! on: " + currentTile);
            }

        }
        else{
            //Miss and switch turns

            System.out.println("Miss on: " + currentTile);
        }
    }
}
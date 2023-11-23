import java.util.*;

public class Player {
    public Board OwnBoard;
    public Board OppBoard;
    public boolean turn;
    public boolean shipsSet;
    public boolean fired;
    public ArrayList<Tile> firedAt;

    public Player (boolean turn, Board own, Board opp) {
        this.turn = turn;
        OwnBoard = own;
        OppBoard = opp;
    }

    public void SetShips (ArrayList<Ship> ships) {
        shipsSet = true;
        OwnBoard.SetShips (ships);
    }

    public boolean gameOver () {
        return OwnBoard.gameOver();
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
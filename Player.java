import java.util.*;

public class Player {
    public Board OwnBoard;
    public Board OppBoard;
    public boolean turn;
    public boolean shipsSet;
    public boolean fired;
    public ArrayList<Tile> firedAt;
    public boolean allSunk;

    public Player (boolean turn, Board own, Board opp) {
        firedAt = new ArrayList<>();
        this.turn = turn;
        OwnBoard = own;
        OppBoard = opp;
    }

    public void SetShips (ArrayList<Ship> ships) {
        shipsSet = true;
        OwnBoard.SetShips (ships);
    }

    public boolean fire (Tile tile) {
        if (!(tile.beenHit()) && this.turn) {
            tile.hit();
            this.firedAt.add(tile);
            // this doesnt work ngl
            allSunk = !OwnBoard.gameOver();
            return true;
        }
        else {
            return false;
        }
    }
}
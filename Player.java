import java.util.*;

public class Player {
    public Board OwnBoard;
    public Board OppBoard;
    public boolean turn;
    public boolean shipsSet;
    public boolean fired;

    public Player (boolean turn, Board own, Board opp) {
        this.turn = turn;
        OwnBoard = own;
        OppBoard = opp;
    }

    public void SetShips (ArrayList<Ship> ships) {
        shipsSet = true;
        OwnBoard.SetShips (ships);
    }
}
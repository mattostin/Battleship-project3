import java.util.*;

public class Player {
    public Board OwnBoard;
    public Board OppBoard;
    public boolean turn;
    public boolean shipsSet;
    public boolean fired;
    public ArrayList<Tile> firedAt;
    public boolean gameOver;

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

    public void gameOver () {
        gameOver = OwnBoard.gameOver();
    }
}
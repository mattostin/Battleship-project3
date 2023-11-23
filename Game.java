import javax.swing.*;

public class Game {

    private static Player player1;
    private static Player player2;
    public static void main(String[] args) {
        Homescreen homescreen = new Homescreen();
        while (true) {
            try {
                Thread.sleep(1000); 
            } catch (Exception e) {
                return;
            }
            if (homescreen.multiplayer) {
                homescreen.setVisible(false);
                Board player1board = new Board();
                Board player2board = new Board();
                player1 = new Player(true, player1board, player2board);
                player2 = new Player(false, player2board, player1board);

                Battleship player1ship = new Battleship(player1);
                Battleship player2ship = new Battleship(player2);
                break;
            }
        }
        if (homescreen.multiplayer) {
            while (true) { 
                try {
                    Thread.sleep(1000); 
                } catch (Exception e) {
                    return;
                }
                if (player1.fired) {
                    player2.turn = true;
                    player1.fired = false;
                }
                if (player2.fired) {
                    player1.turn = true;
                    player2.fired = false;
                }
            }
        }
    }
}
import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        Homescreen homescreen = new Homescreen();
        while (true) {
            try {
                Thread.sleep(1000); 
            } catch (Exception e) {
                return;
            }
            System.out.println(homescreen.multiplayer);
            if (homescreen.multiplayer) {
                homescreen.setVisible(false);
                Board player1board = new Board();
                Board player2board = new Board();
                Player player1 = new Player(true, player1board, player2board);
                Player player2 = new Player(false, player2board, player1board);

                Battleship player1ship = new Battleship(player1);
                Battleship player2ship = new Battleship(player2);
                break;
                

            }
        }
    }
}
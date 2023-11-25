import javax.swing.*;

public class Game {

    private static Player player1 = new Player();
    private static Player player2 = new Player();
    private static Battleship player1ship;
    private static Battleship player2ship;
    public static void main(String[] args) {

        // listens for the end of the game
        Thread gameOver = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    return;
                }
                if (player1.allSunk || player2.allSunk) {                  
                    player1ship.setVisible(false);
                    player2ship.setVisible(false);

                    int p1shots = 0;
                    int p1hits = 0;
                    int p1ships = 0;
                    int p2shots = 0;
                    int p2hits = 0;
                    int p2ships = 0;

                    for (Tile[] a: player1.OwnBoard.tiles) {
                        for (Tile b: a) {
                            if (b.beenHit()) {
                                p2shots++;
                                if (b.hasBoat()) {
                                    p2hits++;
                                }
                            }
                        }
                    }
                    for (Ship a: player1.OwnBoard.ships) {
                        if (a.isDead()) {
                            p2ships++;
                        }
                    }
                    for (Tile[] a: player2.OwnBoard.tiles) {
                        for (Tile b: a) {
                            if (b.beenHit()) {
                                p1shots++;
                                if (b.hasBoat()) {
                                    p1hits++;
                                }
                            }
                        }
                    }
                    for (Ship a: player2.OwnBoard.ships) {
                        if (a.isDead()) {
                            p1ships++;
                        }
                    }
                    EndGame display = new EndGame(player1.allSunk, p1shots, p1hits, p1ships, p2shots, p2hits, p2ships);
                    break;
                }
            }
        });
        gameOver.start();

        Thread runGame = new Thread(() -> {
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

                    player1ship = new Battleship(player1);
                    player2ship = new Battleship(player2);
                    break;
                }
            }
            if (homescreen.multiplayer) {
                while (true) { 
                    try {
                        Thread.sleep(1); 
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
        });
        runGame.start();
    }
}
import javax.swing.*;

public class Game {

    private static Player player1 = new Player();
    private static Player player2 = new Player();
    private static Battleship player1ship;
    private static Battleship player2ship;
    private static boolean start = false;
    private static int[] standardBoardSize = {7, 7};
    private static String[] standardNames = {"Patrol Boat","Destroyer","Submarine", "Battleship","Aircraft Carrier"};
    private static int[] standardShipSize = {2,3,3,4,5};
    public static void main(String[] args) {

        //listens for the end of the game
        Thread gameOver = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    return;
                }
                if (player1.allSunk || player2.allSunk) {                  
                    player1ship.setVisible(false);
                    /*if (player2ship.isVisible()) {
                        player2ship.setVisible(false);
                    }*/
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
                    start = false;
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
                    start = true;
                    homescreen.setVisible(false);
                    Board player1board = new Board(standardBoardSize);
                    Board player2board = new Board(standardBoardSize);
                    player1 = new Player(true, player1board, player2board, standardNames, standardShipSize);
                    player2 = new Player(false, player2board, player1board, standardNames, standardShipSize);

                    player1ship = new Battleship(player1);
                    player2ship = new Battleship(player2);
                    break;
                }
                if (homescreen.computer) {
                    
                    homescreen.setVisible(false);
                    Bot_Selection bot_Selection = new Bot_Selection();
                    bot_Selection.setVisible(true);
                    
                    Board player1board = new Board(standardBoardSize);
                    Board player2board = new Board(standardBoardSize);
                    player1 = new Player(true, player1board, player2board, standardNames, standardShipSize);
                    //once bots work, easy, hard, impossible get rid of this line below and uncomment the bot_selection stuff.
                    //if (easyMode) and hardMode and impossibleMode need to be commented out when the bot_selection screen works.
                    //comment this bottom one out and comment lines 105-113 if it doesn't work
                    //player2 = new Bot(false, player2board, player1board, standardNames, standardShipSize);
                    if (bot_Selection.isEasyMode()){
                        
                        player2 = new Bot(false, player2board, player1board, standardNames, standardShipSize);
                        bot_Selection.setVisible(false);
                        start = true;
                    }
                    if (bot_Selection.isHardMode()){
                        player2 = new HarderBot(false, player2board, player1board, standardNames, standardShipSize);
                        bot_Selection.setVisible(false);
                        start = true;
                    }
                    if (bot_Selection.isImpossibleMode()){
                        player2 = new ImpossibleBot(false, player2board, player1board, standardNames, standardShipSize);
                        bot_Selection.setVisible(false);
                        start = true;
                    }

                    player1ship = new Battleship(player1);
                    break;
                }
            }
            GameStart();
            
        });
        runGame.start();

    }

    public static boolean GameStart () {
        if (start) {
        //System.out.println("Game Started");
            while (true) { 
                try {
                    Thread.sleep(1); 
                } catch (Exception e) {
                    return false;
                }
                if (player1.fired) {
                    //System.out.println("meow");
                    player2.turn = true;
                    player1.fired = false;
                }
                if (player2.fired) {
                    //System.out.println("Player2 Fired");
                    player1.turn = true;
                    player2.fired = false;
                }
                if (!start) {
                    return true;
                }
            }
        } else {return false;}
    }
}
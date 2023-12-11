import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.net.*;

public class server {
    public Player player1;
    public Player player2;
    private BattleshipClient client1;
    private BattleshipClient client2;
    private BufferedReader reader;
    ServerSocket next;
    public int[] boardSize;
    public int[] shipSizes;
    public final int[] defaultShipSizes = {2, 3, 3, 4, 5};
    public String[] shipNames;
    public final String[] defaultShipNames = {"Patrol Boat","Destroyer","Submarine", "Battleship","Aircraft Carrier"};


    public server (int port) {
        try {
            next = new ServerSocket(port);
        } catch (Exception e) {
            return;
        }
    }

    public void placeShips (Player player, ArrayList<Ship> a) {

    }

    public void shoot (Player player, Tile a) {
        if (player.equals(player1)) {
            if (!player1.OppBoard.tiles[a.getX()][a.getY()].beenHit()) {
                if (player1.OppBoard.tiles[a.getX()][a.getY()].hasBoat()) {
                    player1.OppBoard.hit(a.getX(), a.getY());
                    client2.writer.print(a.getX() + "" + a.getY());
                }
                else {
                    player1.OppBoard.hit(a.getX(), a.getY());
                    client2.writer.print(a.getX() + "" + a.getY());
                    player1.turn = false;
                    client2.writer.flush();
                }
            }
        }
        else {
            if (!player2.OppBoard.tiles[a.getX()][a.getY()].beenHit()) {
                if (player2.OppBoard.tiles[a.getX()][a.getY()].hasBoat()) {
                    player2.OppBoard.hit(a.getX(), a.getY());
                    client1.writer.print(a.getX() + "" + a.getY());
                }
                else {
                    player2.OppBoard.hit(a.getX(), a.getY());
                    client1.writer.print(a.getX() + "" + a.getY());
                    player2.turn = false;
                    client1.writer.flush();
                }
            }      
        }
    }

    public void serve(){
        int[] size = {10, 10};
        Board board1 = new Board(size);
        Board board2 = new Board(size);

        while(player2.equals(null)){  
            try {
                Thread.sleep(1);
                Socket clientSocket = next.accept();
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //player needs: boolean turn, Board own, Board opp, String[] shipNames, int[] shipSizes
                if (player1.equals(null)) {
                    player1 = new Player(true, board1, board2, defaultShipNames, defaultShipSizes);
                    client1 = new BattleshipClient(clientSocket, player1, this);
                }
                else if (player2.equals(null)) {
                    player2 = new Player(false, board2, board1, defaultShipNames, defaultShipSizes);
                    client2 = new BattleshipClient(clientSocket, player2, this);
                }
                    
            } catch (Exception e) {
                return;
            }
        }

        while (!player1.shipsSet && !player2.shipsSet) {
            try {
                Thread.sleep(1);
                reader = client1.reader;
                
                

            } catch (Exception e) {
                return;
            }
        }

        while (true) {
            try {



            } catch (Exception e) {
                return;
            }
        }

    }

    private class BattleshipClient extends Thread 
    {
        public Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;
        public server channel;
        private Player player;

        public BattleshipClient (Socket sock, Player player, server server) {
            super();
            this.socket = sock;
            this.channel = server;
            this.player = player;
        }

        @Override
        public void run () {
            try{
                this.writer = new PrintWriter(socket.getOutputStream());
                this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (!player.shipsSet) {
                    String shipsPlaced = "";
                    while (shipsPlaced.equals("")) {
                        shipsPlaced += reader.readLine();
                    }
                    
                    
                    
                    ArrayList<Ship> ShipList = new ArrayList<>();
                    Tile[] temp;

                    int i = 0; 

                    while (i < shipsPlaced.length()) {
                        int j = 0;

                        if (shipsPlaced.charAt(i) == '(') {
                            int num = ((int) shipsPlaced.charAt(i + 1)) - (int) '0';
                            temp = new Tile[num];
                            while (shipsPlaced.charAt(i) != ')') {
                                if (shipsPlaced.charAt(i) == ';') {
                                    temp[j] = new Tile (((int) shipsPlaced.charAt(i - 3) - (int) '0'), ((int) shipsPlaced.charAt(i - 1) - (int) '0'));
                                    j++;
                                }
                                i++;
                            }
                            Ship next = new Ship (temp.length, temp, "", false);
                            ShipList.add(next);
                        }
                        i++;
                    }

                    player1.SetShips(ShipList);

                }

                while (!player1.allSunk) {
                    String shipsPlaced = "";
                    shipsPlaced += reader.readLine();

                    if (player1.turn && !shipsPlaced.equals("")) {
                        Tile shot = new Tile (((int) shipsPlaced.charAt(1) - (int) '0'), ((int) shipsPlaced.charAt(3) - (int) '0'));
                        channel.shoot(player, shot);
                        shipsPlaced = "";
                    }
                }
            }

            catch (Exception e){
            }

        }
    }
}

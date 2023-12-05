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

    }

    public void shoot (Player player, Tile a) {
        
    }

    public void serve(){
        Board board1 = null;
        Board board2 = null;
        String[] namesFinal = null;
        int[] sizesFinal = null;
       
        while(player2.equals(null)){  
            try {
                
                Socket clientSocket = next.accept();
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //player needs: boolean turn, Board own, Board opp, String[] shipNames, int[] shipSizes
                if (player1.equals(null)) {
                    String boardX = reader.readLine();
                    String boardY = reader.readLine();
                    String Ships = reader.readLine(); 
                    int numShips = Integer.parseInt(Ships);
                    namesFinal = new String [numShips];
                    sizesFinal = new int [numShips];
                    String names = reader.readLine();
                    int i = 0; 
                    int j = 0;
                    int idx = 0;
                    while (i < names.length()) {
                        if (names.charAt(i) == ',') {
                            namesFinal[idx] = names.substring(j, i);
                            idx++;
                            j = i + 1;
                        }
                        i++;
                    }
                    String sizes = reader.readLine();
                    i = 0; 
                    j = 0;
                    idx = 0;
                    while (i < sizes.length()) {
                        if (sizes.charAt(i) == ',') {
                            sizesFinal[idx] = Integer.parseInt(sizes.substring(j, i));
                            idx++;
                            j = i + 1;
                        }
                        i++;
                    }

                    int x = Integer.parseInt(boardX);
                    int y = Integer.parseInt(boardY);
                    int[] dimension = {x, y};
                    board1 = new Board(dimension);
                    board2 = new Board(dimension);
                    player1 = new Player(true, board1, board2, namesFinal, sizesFinal);
                    client1 = new BattleshipClient (clientSocket, player1, this);

                }
                else {
                    player2 = new Player(false, board2, board1, namesFinal, sizesFinal);
                    client2 = new BattleshipClient (clientSocket, player1, this);
                    client1.start();
                    client2.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private class BattleshipClient extends Thread{
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

                while (player1.allSunk) {
                    String shipsPlaced = "";

                    if (player1.turn && !shipsPlaced.equals("")) {
                        Tile shot = new Tile (((int) shipsPlaced.charAt(1) - (int) '0'), ((int) shipsPlaced.charAt(3) - (int) '0'));
                        channel.shoot(player, shot);
                    }
                }
          
            }

            catch (Exception e){
            }

        }
    }
}

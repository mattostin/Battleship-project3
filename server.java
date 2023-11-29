import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.net.*;

public class server {
    public Player player1;
    public Player player2;
    private BufferedReader reader;
    ServerSocket next;
    public int[] boardSize;
    public int[] shipSizes;
    public final int[] defaultShipSizes = {2, 3, 3, 4, 5};
    public String[] shipNames;
    public final String[] defaultShipNames = {"Patrol Boat","Destroyer","Submarine", "Battleship","Aircraft Carrier"};

    public server (int port) {

    }

    public void serve(){
        while(player2.equals(null)){
            try {
                Socket clientSocket = next.accept();
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //player needs: boolean turn, Board own, Board opp, String[] shipNames, int[] shipSizes
                String boardX = reader.readLine();
                String boardY = reader.readLine();
                String names = reader.readLine();
                String sizes = reader.readLine();


                BattleshipClient nex = new BattleshipClient (clientSocket, player1, this);
                nex.start();


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
            }catch (Exception e){
            }

        }
    }
}

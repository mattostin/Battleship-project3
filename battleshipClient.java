import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import javax.swing.*;
import java.io.*;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.*;
import java.util.*;

public class battleshipClient {
    private mainframe GUI;
    private Player thisPlayer;
    private Socket socket;
    private BufferedReader reader;
    private static final String finalNames[] = {"Patrol Boat","Destroyer","Submarine", "Battleship","Aircraft Carrier"};
    private boolean sentShips;
    
    
    private PrintWriter writer;
    public boolean connected;

    public battleshipClient(Player thisPlayer, String ip, int port){
        this.thisPlayer = thisPlayer;
        try {
            this.socket = new Socket (ip, port);
            this.writer = new PrintWriter (socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            connected = true;
            new ReadingThread().start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(GUI,"IP/Port Invalid","Connection Error", JOptionPane.ERROR_MESSAGE);
            connected = false;  
            return;
        }

    }

    private class ReadingThread extends Thread {
        int[] array = {2, 3, 3, 4, 5};
        @Override
        public void run() {
            try {
                while (true) {
                    String message = reader.readLine();

                    if(message.charAt(0) == '&' && !thisPlayer.OppBoard.shipsSet){
                        ArrayList<Ship> ships = new ArrayList<>();
                        int k = 1;
                        for (int i = 0; i < 5; i ++) {
                            Tile tiles[] = new Tile[array[i]];
                            int idx = 0;
                            for (int j = 0; j < array[i]; j++) {
                                int x = ((int) message.charAt(k)) - (int) '0';
                                int y = ((int) message.charAt(k + 1)) - (int) '0';
                                k+=2; 
                                tiles[idx] = new Tile (x, y);
                                idx++;
                            }
                            ships.add(new Ship(array[i], tiles, finalNames[i], false)); 
                        }
                        thisPlayer.OppBoard.SetShips(ships);
                    } else {
                        ArrayList<Tile> tiles = new ArrayList();
                        int length = message.length();
                        int x = -1;
                        int y = -1;

                        for (int i = 0; i < length; i+=2) {
                            x = message.charAt(i);
                            y = message.charAt(i+1);

                            tiles.add(new Tile(x, y));
                        }

                        for (Tile a: tiles) {
                            thisPlayer.OppBoard.hit(a.getX(), a.getY());
                        }

                        if (thisPlayer.OwnBoard.shipsSet && !sentShips) {
                            writer.print('&');
                            for (Ship a: thisPlayer.OwnBoard.ships) {
                                for (Tile b: a.location) {
                                    writer.print(b.getX() + "" + b.getY());
                                }
                            }
                            writer.flush();
                            sentShips = true;
                        }
                    }
                }
            } catch (Exception e) {
                if(socket.isClosed()){
                    System.out.println("Lost connection");
                }
            }
        } 
    }
}
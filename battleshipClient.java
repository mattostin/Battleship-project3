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

            writer.println(thisPlayer.OwnBoard.boardSize[0]);
            writer.println(thisPlayer.OwnBoard.boardSize[0]);
            writer.println(Arrays.toString(thisPlayer.names));
            writer.println(Arrays.toString(thisPlayer.sizes));
            writer.flush();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(GUI,"IP/Port Invalid","Connection Error", JOptionPane.ERROR_MESSAGE);
            connected = false;  
            return;
        }

    }

    private class ReadingThread extends Thread {
    }

}

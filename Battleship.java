import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




public class Battleship extends JFrame{
    // Create the battleship grid and text fields
    private JTextField battleshipGrid;
    private JButton firebutton;
    private JTextField Xaxis;
    private JTextField Yaxis;

    public static String nameOfShip[] = {"Submarine","Destroyer","Submarine", "Cruiser","Battleship"};
    public static int sizeOfShip[] = {2,3,3,4,5}; // represents size of the ship
    public static int xyBoardSize[] = {10, 10};



    public void BattleshipGUI() {
        setTitle("Battlehip!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
                
        Font customFont = new Font("Arial", Font.PLAIN, 14);

    }
}
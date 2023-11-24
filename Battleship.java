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

    private void createGrid(){
        for(int i = 0; i <10; i++);
        for(int j = 0; j < 10; j++){
            JLabel cell = new JLabel("", SwingConstants.CENTER);
            cell.setBorder(BorderFactory.createLineBorder(Color.white));
            add(cell);
        }
    }

    public void BattleshipGUI() {
        setTitle("Battleship!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new GridLayout(10,10));
        createGrid();
        setVisible(true);
        Font customFont = new Font("Arial", Font.PLAIN, 14);

        firebutton = new JButton("FIRE!");
        Xaxis = new JTextField("1");
        Xaxis = new JTextField("2");
        Xaxis = new JTextField("3");






  }

  public static void main (String[] args){
    SwingUtilities.invokeLater(() -> new Battleship());
  }
}
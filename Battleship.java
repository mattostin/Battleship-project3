import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;




public class Battleship extends JFrame{

    private JButton[][] buttons;
    private JButton firebutton;

    public static String nameOfShip[] = {"Submarine","Destroyer","Submarine", "Cruiser","Battleship"};
    public static int sizeOfShip[] = {2,3,3,4,5}; // represents size of the ship
    public static int xyBoardSize[] = {10, 10};


    public Battleship () {
        super("Battleship Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buttons = new JButton[10][10];
        JPanel boardPanel = new JPanel(new GridLayout(10, 10));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setForeground(Color.BLUE);
                buttons[i][j].setOpaque(true);
                buttons[i][j].setBorderPainted(true);
                boardPanel.add(buttons[i][j]);
                
                final int row = i;
                final int col = j;

                buttons[i][j].addActionListener(new ActionListener() {

                    
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Button pressed at: " + (row + 1) + "-" + (char)('A' + col));
                    }
                });
            }
        }

        JPanel rowLabelPanel = new JPanel(new GridLayout(10, 1));
        for (int i = 1; i <= 10; i++) {
            JLabel label = new JLabel(Integer.toString(i));
            label.setHorizontalAlignment(JLabel.CENTER);
            rowLabelPanel.add(label);
        }

        JPanel colLabelPanel = new JPanel(new GridLayout(1, 11)); 
        colLabelPanel.add(new JLabel());

        for (int c = (int)'A'; c <= (int)'J'; c++) {
            JLabel label = new JLabel(Character.toString(c));
            label.setHorizontalAlignment(JLabel.CENTER);
            colLabelPanel.add(label);
        }
        
        JPanel grid = new JPanel();
        grid.setLayout(new BorderLayout ());

        grid.add(rowLabelPanel, BorderLayout.WEST);
        grid.add(colLabelPanel, BorderLayout.NORTH);
        grid.add(boardPanel, BorderLayout.CENTER);

        JButton fire = new JButton("FIRE");
        add(grid, BorderLayout.NORTH);
        add(fire, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Battleship());

    }
}
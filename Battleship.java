import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class Battleship extends JFrame{
    private Board board;
    public static String nameOfShip[] = {"Patrol Boat","Destroyer","Submarine", "Cruiser","Battleship"};
    public static int sizeOfShip[] = {2,3,3,4,5}; // represents size of the ship
    public int shipsPlaced = 0;
    public static int xyBoardSize[] = {10, 10};
    private JButton[][] buttons;
    public Tile currentTile;
    public ArrayList<Ship> ships;
    public JTextField info;
    public boolean rotate = false; 

    public Battleship (Player player) {
        super("Battleship Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ArrayList<Ship> ships = new ArrayList<>();
        board = player.OwnBoard;
        currentTile = board.getTile(0,0);

        buttons = new JButton[xyBoardSize[0]][xyBoardSize[1]];
        JPanel boardPanel = new JPanel(new GridLayout(xyBoardSize[0], xyBoardSize[1]));

        info = new JTextField();
        info.setEditable(false);

        for (int i = 0; i < xyBoardSize[0]; i++) {
            for (int j = 0; j < xyBoardSize[1]; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension (50, 50)); 
                buttons[i][j].setBackground(Color.CYAN);
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setOpaque(true);
                boardPanel.add(buttons[i][j]);

                final int x = i;
                final int y = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        currentTile = board.getTile(x, y);
                    }
                });
            }
        }

        JPanel rowLabelPanel = new JPanel(new GridLayout(xyBoardSize[0], 1));
        for (int i = 1; i <= xyBoardSize[0]; i++) {
            JLabel label = new JLabel(Integer.toString(i));
            label.setHorizontalAlignment(JLabel.CENTER);
            rowLabelPanel.add(label);
        }

        JPanel colLabelPanel = new JPanel(new GridLayout(1, xyBoardSize[1])); 
        colLabelPanel.add(new JLabel());

        for (int c = (int)'A'; c < ((int)'A') + xyBoardSize[1]; c++) {
            JLabel label = new JLabel(Character.toString(c));
            label.setHorizontalAlignment(JLabel.CENTER);
            colLabelPanel.add(label);
        }
        
        JPanel grid = new JPanel();
        grid.setLayout(new BorderLayout ());

        grid.add(rowLabelPanel, BorderLayout.WEST);
        grid.add(colLabelPanel, BorderLayout.NORTH);
        grid.add(boardPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout ());

        JButton set = new JButton ("Set Ship");
        JButton resetShips = new JButton ("Reset All Ships");
        JButton rotateButton = new JButton ("Rotate ship");
        JButton finalize = new JButton ("Finalize");

        info.setText("Add " + nameOfShip[shipsPlaced] + ", Length " + sizeOfShip[shipsPlaced]);

        bottom.add(set, BorderLayout.CENTER);
        bottom.add(resetShips, BorderLayout.WEST);
        bottom.add(finalize, BorderLayout.EAST);

        JButton fire = new JButton("FIRE");

        set.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (shipsPlaced < sizeOfShip.length) {
                    if (rotate) {
                        if ((currentTile.getX() + sizeOfShip[shipsPlaced]) <= xyBoardSize[0]) {
                            Tile[] location = new Tile[sizeOfShip[shipsPlaced]];
                            
                            for (int i = 0; i < sizeOfShip[shipsPlaced]; i++) {
                                location[i] = new Tile (currentTile.getX() + i, currentTile.getY());
                            }

                            ships.add(new Ship(sizeOfShip[shipsPlaced], location));
                            shipsPlaced++;
                            if (shipsPlaced < sizeOfShip.length)
                            info.setText("Add " + nameOfShip[shipsPlaced] + ", Length " + sizeOfShip[shipsPlaced]);
                            
                        }
                    }
                    else {
                        if ((currentTile.getY() + sizeOfShip[shipsPlaced]) <= xyBoardSize[1]) {
                            Tile[] location = new Tile[sizeOfShip[shipsPlaced]];
                            
                            for (int i = 0; i < sizeOfShip[shipsPlaced]; i++) {
                                location[i] = new Tile (currentTile.getX(), currentTile.getY() + i);
                            }

                            ships.add(new Ship(sizeOfShip[shipsPlaced], location));
                            shipsPlaced++;
                            if (shipsPlaced < sizeOfShip.length)
                            info.setText("Add " + nameOfShip[shipsPlaced] + ", Length " + sizeOfShip[shipsPlaced]);
                        }
                    }
                }
            }
        });

        finalize.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (shipsPlaced == sizeOfShip.length) {
                    bottom.remove(finalize);
                    bottom.remove(set);
                    bottom.remove(resetShips);
                    bottom.add(fire);
                    bottom.revalidate();
                    bottom.repaint();
                    player.SetShips(ships);
                    info.setText("BEGIN");
                }
            }
        });

        fire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!(currentTile.beenHit())) {
                    currentTile.hit();
                    if (currentTile.hasBoat()) {
                        buttons[currentTile.getX()][currentTile.getY()].setBackground(Color.RED);
                    }
                    else {
                        buttons[currentTile.getX()][currentTile.getY()].setBackground(Color.GRAY);
                    }
                }
            }
        });

        add(info, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }
}
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import java.net.*;

public class Battleship extends JFrame{
    private Board board;
    private Board oppBoard;
    public static String nameOfShip[] = {"Patrol Boat","Destroyer","Submarine", "Cruiser","Battleship"};
    public static int sizeOfShip[] = {2,3,3,4,5}; // represents size of the ship
    public int shipsPlaced = 0;
    public static int xyBoardSize[] = {10, 10};
    private JButton[][] buttons;
    private JPanel[][] opp;
    public Tile currentTile;
    public ArrayList<Ship> ships;
    public JTextField info;
    public boolean rotate = false; 
    public Player player;
    public boolean ready = false;
    public ArrayList<Tile> temporaryTiles;
    public ArrayList<Tile> newBoatTiles;

    //player logic

    public Player player1;
    public Player player2;



    public Battleship (Player player) {
        super("Battleship Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.player = player;
        ships = new ArrayList<>();
        temporaryTiles = new ArrayList<>();
        newBoatTiles = new ArrayList<>();

        board = player.OwnBoard;
        oppBoard = player.OppBoard;
        currentTile = oppBoard.getTile(0,0);

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
                        currentTile = oppBoard.getTile(x, y);
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

        JPanel rowLabelPanel2 = new JPanel(new GridLayout(xyBoardSize[0], 1));
        for (int i = 1; i <= xyBoardSize[0]; i++) {
            JLabel label = new JLabel(Integer.toString(i));
            label.setHorizontalAlignment(JLabel.CENTER);
            rowLabelPanel2.add(label);
        }

        JPanel colLabelPanel2 = new JPanel(new GridLayout(1, xyBoardSize[1])); 
        colLabelPanel.add(new JLabel());

        for (int c = (int)'A'; c < ((int)'A') + xyBoardSize[1]; c++) {
            JLabel label = new JLabel(Character.toString(c));
            label.setHorizontalAlignment(JLabel.CENTER);
            colLabelPanel2.add(label);
        }
        
        JPanel grid = new JPanel();
        grid.setLayout(new BorderLayout ());

        grid.add(rowLabelPanel, BorderLayout.WEST);
        grid.add(colLabelPanel, BorderLayout.NORTH);
        grid.add(boardPanel, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());

        JButton set = new JButton ("Set Ship");
        JButton resetShips = new JButton ("Reset All Ships");
        JButton rotateButton = new JButton ("Rotate ship");
        JButton finalize = new JButton ("Finalize");

        info.setText("Add " + nameOfShip[shipsPlaced] + ", Length " + sizeOfShip[shipsPlaced]);

        bottom.add(set);
        bottom.add(rotateButton);
        
        // FINISH LATER
        bottom.add(resetShips);

        bottom.add(finalize);

        JButton fire = new JButton("FIRE");

        set.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newBoatTiles = new ArrayList<>();
                if (shipsPlaced < sizeOfShip.length) {
                    if (rotate) {
                        if ((currentTile.getX() + sizeOfShip[shipsPlaced]) <= xyBoardSize[0]) {
                            Tile[] location = new Tile[sizeOfShip[shipsPlaced]];
                            
                            for (int i = 0; i < sizeOfShip[shipsPlaced]; i++) {
                                location[i] =  board.getTile(currentTile.getX() + i, currentTile.getY());
                                if (temporaryTiles.contains(location[i])) {
                                    flashError("Boat Already There");
                                    return;
                                }
                                newBoatTiles.add(location[i]);
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
                                location[i] = board.getTile(currentTile.getX(), currentTile.getY() + i);
                                if (temporaryTiles.contains(location[i])) {
                                    flashError("Boat Already There");
                                    return;
                                }
                                newBoatTiles.add(location[i]);    
                            }

                            ships.add(new Ship(sizeOfShip[shipsPlaced], location));
                            shipsPlaced++;
                            if (shipsPlaced < sizeOfShip.length)
                            info.setText("Add " + nameOfShip[shipsPlaced] + ", Length " + sizeOfShip[shipsPlaced]);
                        }
                    }  
                }
                for (Tile a: newBoatTiles) {
                    opp[a.getX()][a.getY()].setBackground(Color.GRAY);
                    temporaryTiles.add(a);
                }
            }
        });

        finalize.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (shipsPlaced == sizeOfShip.length) {
                    bottom.remove(finalize);
                    bottom.remove(set);
                    bottom.remove(resetShips);
                    bottom.remove(rotateButton);
                    bottom.add(fire);
                    bottom.revalidate();
                    bottom.repaint();
                    player.SetShips(ships);
                    ready = true;
                    info.setText("BEGIN");
                }
            }
        });

        rotateButton.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rotate = !rotate;
            }
        });

//fire button
        fire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!(currentTile.beenHit()) && player.turn) {
                    currentTile.hit();
                    player.firedAt.add(currentTile);
                    if (currentTile.hasBoat()) {
                        buttons[currentTile.getX()][currentTile.getY()].setBackground(Color.RED);
                    }
                    else {
                        buttons[currentTile.getX()][currentTile.getY()].setBackground(Color.LIGHT_GRAY);
                        player.fired = true;
                        //made it player 1 might have to change later for functionality reasons.
                        player1.simulateMove(currentTile);
                    }
                }
            }
        });
        

        opp = new JPanel[xyBoardSize[0]][xyBoardSize[1]];
        JPanel oppPanel = new JPanel(new GridLayout(xyBoardSize[0], xyBoardSize[1]));

        JPanel oppGrid = new JPanel();
        oppGrid.setLayout(new BorderLayout ());

        for (int i = 0; i < xyBoardSize[0]; i++) {
            for (int j = 0; j < xyBoardSize[1]; j++) {
                opp[i][j] = new JPanel();
                opp[i][j].setPreferredSize(new Dimension (50, 50)); 
                opp[i][j].setBackground(Color.CYAN);
                opp[i][j].setBorder(new LineBorder(Color.BLACK));
                oppPanel.add(opp[i][j]);
            }
        }

        oppGrid.add(rowLabelPanel2, BorderLayout.WEST);
        oppGrid.add(colLabelPanel2, BorderLayout.NORTH);
        oppGrid.add(oppPanel, BorderLayout.CENTER);
        

        add(oppGrid, BorderLayout.EAST);
        add(info, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        

        pack();
        setLocationRelativeTo(null); 
        setVisible(true);

        TurnChecker isTurn = new TurnChecker ();

        isTurn.start();


    }
    private class TurnChecker extends Thread {
        public TurnChecker () {
            super();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000); 
                } catch (Exception e) {
                    return;
                }
                if (ready) {
                    if (player.turn) {
                        info.setText("Your Turn");
                    }
                    else {
                        info.setText("Waiting For Your Turn");
                    }
                }
            }
        }
    }

    public void flashError (String e) {
        JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
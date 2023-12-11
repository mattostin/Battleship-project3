import javax.swing.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Battleship extends JFrame{
    private Board board;
    private Board oppBoard;
    public String[] nameOfShip;
    public static final String finalNames[] = {"Patrol Boat","Destroyer","Submarine", "Battleship","Aircraft Carrier"};
    public static final String nameOfImage[] = {"patrol", "destroyer", "sub", "battleship", "carrier"};
    public int[] sizeOfShip; // represents size of the ship
    public static final int finalSizes[] = {2,3,3,4,5}; 
    public int shipsPlaced = 0;
    public int[] xyBoardSize;
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
    public JFrame middle;

    public Battleship (Player player) {
        super("Battleship Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.player = player;
        this.nameOfShip = player.names;
        this.sizeOfShip = player.sizes;
        this.xyBoardSize = player.OwnBoard.boardSize;
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
        //JButton resetShips = new JButton ("Reset All Ships");
        JButton rotateButton = new JButton ("Rotate >");
        JButton finalize = new JButton ("Finalize");

        info.setText("Add " + nameOfShip[shipsPlaced] + ", Length " + sizeOfShip[shipsPlaced]);

        bottom.add(set);
        bottom.add(rotateButton);
        
        // FINISH LATER
        //bottom.add(resetShips);

        bottom.add(finalize);

        JButton fire = new JButton("FIRE");


        //Got idea to use VK_ENTER and VK_F from this
        //
        //https://stackoverflow.com/questions/22443301/key-listener-not-working?rq=4
        fire.setMnemonic(KeyEvent.VK_F);
        fire.setMnemonic(KeyEvent.VK_ENTER);


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

                            ships.add(new Ship(sizeOfShip[shipsPlaced], location, nameOfShip[shipsPlaced], rotate));
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

                            ships.add(new Ship(sizeOfShip[shipsPlaced], location, nameOfShip[shipsPlaced], rotate));
                            shipsPlaced++;
                            if (shipsPlaced < sizeOfShip.length)
                            info.setText("Add " + nameOfShip[shipsPlaced] + ", Length " + sizeOfShip[shipsPlaced]);
                        }
                    }
                    boolean drawn = displayBoat(newBoatTiles, nameOfShip[shipsPlaced - 1], sizeOfShip[shipsPlaced - 1], rotate);
                    for (int a = 0; a < newBoatTiles.size(); a++) {
                        Tile tile = newBoatTiles.get(a);
                        if (!drawn) {
                            opp[tile.getX()][tile.getY()].setBackground(Color.GRAY);
                        }
                        temporaryTiles.add(tile);  
                    } 
                }
            }
        });

        finalize.addActionListener (new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (shipsPlaced == sizeOfShip.length) {
                    bottom.remove(finalize);
                    bottom.remove(set);
                    //bottom.remove(resetShips);
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
                if (rotate) {
                    rotateButton.setText("Rotate v");
                }
                else {
                    rotateButton.setText("Rotate >");
                }
            }
        });

       fire.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {        
                boolean breaker = player.fire(currentTile);
                if (breaker) {
                    if (currentTile.hasBoat()) {
                        buttons[currentTile.getX()][currentTile.getY()].setBackground(Color.RED);
                    }
                    else {
                        buttons[currentTile.getX()][currentTile.getY()].setBackground(Color.LIGHT_GRAY);
                        player.fired = true;
                        player.turn = false;
                    }
                }
            }
        });

        //NOT WORKING YET
        //credit (this.requestFocus();) https://gamedev.stackexchange.com/questions/30460/how-can-i-implement-keylisteners-actionlisteners-into-the-jframe 
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key Pressed");
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (ready) {
                        fire.doClick();
                    }
                    else {
                        set.doClick();
                    }
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {}
            @Override
            public void keyTyped(KeyEvent e) {}
        });
        this.requestFocus();

        opp = new JPanel[xyBoardSize[0]][xyBoardSize[1]];
        JPanel oppPanel = new JPanel(new GridLayout(xyBoardSize[0], xyBoardSize[1]));

        JPanel oppGrid = new JPanel();
        oppGrid.setLayout(new BorderLayout ());

        for (int i = 0; i < xyBoardSize[0]; i++) {
            for (int j = 0; j < xyBoardSize[1]; j++) {
                opp[i][j] = new JPanel();
                addPicture("water1.jpg", opp[i][j], false);
                oppPanel.add(opp[i][j]);
            }
        }

        oppGrid.add(rowLabelPanel2, BorderLayout.WEST);
        oppGrid.add(colLabelPanel2, BorderLayout.NORTH);
        oppGrid.add(oppPanel, BorderLayout.CENTER);

        JPanel middle = new JPanel ();
        middle.setLayout (new BorderLayout());    

        middle.add(new JLabel("Enemy Ships:"), BorderLayout.NORTH);

        add(oppGrid, BorderLayout.EAST);
        add(info, BorderLayout.NORTH);
        add(grid, BorderLayout.WEST);
        //add(middle, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        

        pack();
        setResizable(false);
        setVisible(true);

        TurnChecker isTurn = new TurnChecker ();
        isTurn.start();

        AmbientAnimator waves = new AmbientAnimator ();
        waves.start();


       
    }

    private class AmbientAnimator extends Thread {        
        
        public AmbientAnimator () {
            super();
        }

        @Override
        public void run () {
            while (true) {
                try {
                    Thread.sleep(100); 
                } catch (Exception e) {
                    return;
                }

                /* if (player.OppBoard.shipsSet) {
                    JFrame middle = new JFrame();
                    middle.setLayout(new FlowLayout());
                    for (Ship a: player.OppBoard.ships) {
                        String display = a.shipName + ": ";
                        if (a.isDead()) {
                            display += "destroyed";
                            JLabel shipStatus = new JLabel(display);
                            shipStatus.setForeground(Color.red);
                            middle.add(shipStatus);
                        }
                        else {
                            JLabel shipStatus = new JLabel(display);
                            shipStatus.setForeground(Color.black);
                            middle.add(shipStatus);
                        }  
                    }
                    middle.revalidate();
                    middle.repaint();
                } */
                for (int i = 0; i < xyBoardSize[0]; i++) {
                    for (int j = 0; j < xyBoardSize[1]; j++) {
                        if (player.OwnBoard.getTile(i, j).beenHit() && !player.OwnBoard.getTile(i, j).showSunk) {
                            if (player.OwnBoard.getTile(i, j).hasBoat()) {
                                Tile temp = player.OwnBoard.getTile(i, j);
                                temp.showSunk = true;
                                opp[i][j].removeAll();
                                //opp[i][j].setBackground(Color.RED);
                                String name = "destroyed";
                                for (int c = 0; c < finalNames.length; c++) {
                                    if (temp.name.equals(finalNames[c])) {
                                        //System.out.println(finalNames[c] + " " + temp.name);
                                        name += nameOfImage[c];
                                    }
                                }
                                name+= temp.tileNo + ".jpg";
                                addPicture(name, opp[i][j], temp.rotate);
                                opp[i][j].revalidate();
                                opp[i][j].repaint();
                            }
                            else {
                                opp[i][j].removeAll();
                                opp[i][j].setBackground(Color.LIGHT_GRAY);
                                opp[i][j].revalidate();
                                opp[i][j].repaint();
                            }
                        }  
                        else if (!player.OppBoard.getTile(i, j).beenHit() && player.OppBoard.getTile(i, j).equals(currentTile)) {
                            buttons[i][j].setBackground(Color.yellow);
                        }
                        else if (player.OppBoard.getTile(i, j).beenHit() && player.OppBoard.getTile(i, j).hasBoat()) {
                            buttons[i][j].setBackground(Color.RED);
                            if (player.OppBoard.getTile(i, j).ship.isDead()) {
                                buttons[i][j].setBackground(Color.PINK);
                            }
                        }
                        else if (player.OppBoard.getTile(i, j).beenHit() && !(player.OppBoard.getTile(i, j).hasBoat())) {
                            buttons[i][j].setBackground(Color.lightGray);
                        }
                        else if (!player.OppBoard.getTile(i, j).beenHit()) {
                            buttons[i][j].setBackground(Color.cyan);
                        }
                    }
                }
            }
        }
    }
    
    private class TurnChecker extends Thread {
        public TurnChecker () {
            super();
        }

        @Override
        public void run () {
            while (true) {
                try {
                    Thread.sleep(100); 
                } catch (Exception e) {
                    return;
                }
                if (player.allSunk) {
                    info.setText("Game Over");
                } else {
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
    }

    public void flashError (String e) {
        JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void addPicture (String fileName, JPanel tile, boolean rotated) {
        //source: https://stackoverflow.com/questions/6444042/java-resize-image-dynamically-to-fit-grids-in-gridlayout
        //System.out.println(fileName);
        try {
            tile.removeAll();   

            BufferedImage picture = ImageIO.read(new File(fileName));
            
            if (rotated) {
                //source: https://stackoverflow.com/questions/15779877/rotate-bufferedimage-inside-jpanel
                int width = picture.getWidth();
                int height = picture.getHeight();

                BufferedImage rotatedImage = new BufferedImage(height, width, picture.getType());
                
                Graphics2D graphic = rotatedImage.createGraphics();
                graphic.rotate(Math.toRadians(90), width/2, height/2);
                graphic.drawImage(picture, null, 0, 0);
                graphic.dispose();

                picture = rotatedImage;
            }
            Image resizedImage = picture.getScaledInstance(50, 50, Image.SCALE_SMOOTH); 

            ImageIcon pictureIcon = new ImageIcon(resizedImage);
            JLabel label = new JLabel(pictureIcon);

            tile.setLayout(new BorderLayout());
            tile.add(label, BorderLayout.CENTER);
            tile.setBorder(new LineBorder(Color.BLACK));
            tile.setPreferredSize(new Dimension(50,50));

            tile.revalidate();
            this.repaint();

        } catch (IOException e) {
            return;
        }
    }

    public boolean displayBoat (ArrayList<Tile> tiles, String name, int len, boolean rotated) {
        ArrayList<JPanel> panels = new ArrayList<>();
        for (Tile a: tiles) {
            //adding the corresponding tiles to an ArrayList of JPanels (easy to send to the image method)
            panels.add(opp[a.getX()][a.getY()]);
        }

        String imageName = "";

        for (int i = 0; i < finalNames.length; i++) {
            if (name.equals(finalNames[i])) {
                imageName = nameOfImage[i];
            }
        }
        if (imageName.equals("")) {
            for (int i = 0; i < finalSizes.length; i++) {
                if (len == finalSizes[i]) {
                    imageName = nameOfImage[i];
                }
            }
        }

        if (!imageName.equals("")) {
            for (int i = 1; i <= len; i++) {
                String imageName2 = imageName + i + ".jpg";
                addPicture(imageName2, panels.get(panels.size() - i), rotated);
            }
            return true;
        }
        return false;
    }
}
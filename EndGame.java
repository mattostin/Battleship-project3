import javax.swing.*;
import java.awt.Font;
import java.awt.*;

public class EndGame extends JFrame {
    private JLabel Head; 
    private static Font Header = new Font ("Arial", Font.PLAIN, 24);

    public EndGame (boolean victory, int shotsTaken, int shotsHit, int shipsSunk, int shotsTakenAgainst, int shotsHitAgainst, int shipsSunkAgainst) {      
        super ("GAME OVER");
        if (victory) {    
            Head = new JLabel ("VICTORY");
            Head.setFont(Header);
        }
        else {
            Head = new JLabel ("DEFEATED");
            Head.setFont(Header);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());     

        JPanel ownStats = new JPanel();
        ownStats.setLayout(new BorderLayout());

        ownStats.add(new JLabel("SHOTS FIRED: " + shotsTaken), BorderLayout.NORTH);
        ownStats.add(new JLabel("SHOTS LANDED: " + shotsHit), BorderLayout.CENTER);
        ownStats.add(new JLabel("SHIPS DESTROYED: " + shipsSunk), BorderLayout.SOUTH);

        JPanel oppStats = new JPanel();
        oppStats.setLayout(new BorderLayout());
        oppStats.add(new JLabel("SHOTS AGAINST: " + shotsTakenAgainst), BorderLayout.NORTH);
        oppStats.add(new JLabel("HITS TAKEN: " + shotsHitAgainst), BorderLayout.CENTER);
        oppStats.add(new JLabel("SHIPS LOST: " + shipsSunkAgainst), BorderLayout.SOUTH);


        add(Head, BorderLayout.NORTH);
        add(ownStats, BorderLayout.WEST);
        add(oppStats, BorderLayout.EAST);
        
        pack();
        setSize(300, 200);
        setResizable(false);
        setVisible(true);
    }
}
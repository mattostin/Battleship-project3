import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Homescreen extends JFrame {

    public boolean multiplayer = false;
    public boolean computer = false;
    public boolean campaign = false;

    public Homescreen() {
        super("Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton multiplayerButton = new JButton("Multiplayer");
        JButton computerButton = new JButton("Computer");
        JButton campaignButton = new JButton("Campaign");

        multiplayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                multiplayer = true;
            }
        });

        computerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computer = true;
            }
        });

        campaignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campaign = true;
            }
        });

        add(multiplayerButton);
        add(computerButton);
        add(campaignButton);

        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    
}
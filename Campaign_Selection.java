import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Campaign_Selection extends JFrame {

    public boolean easyCampaign = false;
    public boolean hardCampaign = false;
    public boolean impossibleCampaign = false;

    public Campaign_Selection() {
        super("Campaign Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton easy_Campaign_Button = new JButton("Easy Mode");
        JButton hard_Campaign_Button = new JButton("Hard Mode");
        JButton impossible_Campaign_Button = new JButton("Impossible Mode");

        easy_Campaign_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyCampaign = true;
            }
        });

        hard_Campaign_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hardCampaign = true;
            }
        });

        impossible_Campaign_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                impossibleCampaign = true;
            }
        });


        

        add(easy_Campaign_Button);
        add(hard_Campaign_Button);
        add(impossible_Campaign_Button);

        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }
    
    
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Bot_Selection extends JFrame {

    public boolean easyMode = false;
    public boolean hardMode = false;
    public boolean impossibleMode = false;

    public Bot_Selection() {
        super("Bot Difficulty Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton easy_Button = new JButton("Easy");
        JButton hard_Button = new JButton("Hard");
        JButton impossible_Button = new JButton("Impossible");

        easy_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                easyMode = true;
            }
        });

        hard_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hardMode = true;
            }
        });

        impossible_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                impossibleMode = true;
            }
        });


        

        add(easy_Button);
        add(hard_Button);
        add(impossible_Button);

        pack();
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public boolean isEasyMode(){
        return easyMode;
    }

    public boolean isHardMode(){
        return hardMode;
    }

    public boolean isImpossibleMode(){
        return impossibleMode;
    }
    
}
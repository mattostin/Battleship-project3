import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainframe extends JFrame {
    private JTextField ipTextField;
    private JTextField portTextField;

    public mainframe() {
        setSize(500,100);
        setTitle("Connect to Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel ipLabel = new JLabel("IP Address:");
        JLabel portLabel = new JLabel("Port:");

        ipTextField = new JTextField(40);
        portTextField = new JTextField(40);

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipTextField.getText();
                String port = portTextField.getText();

            }
        });

        JPanel ip = new JPanel();
        ip.setLayout(new BorderLayout());

        ip.add(ipLabel, BorderLayout.WEST);
        ip.add(ipTextField, BorderLayout.EAST);

        JPanel port = new JPanel();
        port.setLayout(new BorderLayout());

        port.add(portLabel, BorderLayout.WEST);
        port.add(portTextField, BorderLayout.EAST);
        port.add(connectButton, BorderLayout.SOUTH);

        add(ip, BorderLayout.NORTH);
        add(port, BorderLayout.SOUTH);



    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new mainframe().setVisible(true);
            }
        });
    }
}

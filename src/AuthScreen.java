import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthScreen extends JFrame {

    public AuthScreen() {

        JFrame authFrame = new JFrame("Authorization");
        authFrame.setSize(900,700);
        authFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        authFrame.setLocationRelativeTo(null);
        authFrame.setLayout(new GridLayout(3,2));

        JLabel usrLabel = new JLabel("Login:");
        JTextField usrTF = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passTF = new JPasswordField();

        JButton btnLogin = getLoginBtn(authFrame, usrTF, passTF);

        JButton btnRegister = getRegisterBtn(usrTF, passTF);

        authFrame.add(usrLabel);
        authFrame.add(usrTF);
        authFrame.add(passLabel);
        authFrame.add(passTF);
        authFrame.add(btnLogin);
        authFrame.add(btnRegister);
        authFrame.setVisible(true);
    }

    private static JButton getLoginBtn(JFrame authFrame, JTextField usrTF, JPasswordField passTF) {
        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usrTF.getText();
                String pass = new String(passTF.getPassword());

                if (Authorization.auth(user, pass)) {
                    JOptionPane.showMessageDialog(null,"Welcome " + user);
                    authFrame.dispose();
                    callMainScreen();
                }
                else JOptionPane.showMessageDialog(null,"Invalid data", "Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        return btnLogin;
    }

    private static JButton getRegisterBtn(JTextField usrTF, JPasswordField passTF) {
        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usrTF.getText();
                String pass = new String(passTF.getPassword());

                if (user.isEmpty() || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Insert all data");
                    return;
                }

                if (Authorization.isRegister(user,pass)) JOptionPane.showMessageDialog(null, "Success");
                else JOptionPane.showMessageDialog(null,"User is already register");
            }
        });
        return btnRegister;
    }

    private static void callMainScreen() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mini CRM system");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);

            MainScreen mainScreen = new MainScreen();
            frame.setContentPane(mainScreen.getMainPanel());

            frame.setVisible(true);
        });
    }
}

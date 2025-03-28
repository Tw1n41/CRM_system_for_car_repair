import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {
    private javax.swing.JPanel JPanel;

    public MainScreen() {

        JPanel.setLayout(new GridLayout(0,1));

        JTextArea textArea1 = new JTextArea();

//      Pressing the button opens a screen with clients
        JButton btnClients = getjButtonClients(textArea1);

        JButton btnPositions = getjButtonPositions(textArea1);

        JButton btnEmployees = getjButtonEmployees(textArea1);

        JButton btnStorage = getjButtonStorage(textArea1);

        JButton btnDetails = getjButtonDetails(textArea1);

        JButton btnOrders = getjButtonOrder(textArea1);

        JPanel.add(btnClients);
        JPanel.add(btnPositions);
        JPanel.add(btnEmployees);
        JPanel.add(btnStorage);
        JPanel.add(btnDetails);
        JPanel.add(btnOrders);
        JPanel.add(textArea1);
        JPanel.revalidate();
        JPanel.repaint();
    }

    private static JButton getjButtonClients(JTextArea textArea1) {
        JButton btnClients = new JButton("Clients");
        btnClients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("Go to clients screen");
                new ClientScreen();
            }
        });
        return btnClients;
    }

    private static JButton getjButtonPositions(JTextArea textArea1) {
        JButton btnPositions = new JButton("Positions");
        btnPositions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("Go to positions screen");
                new PositionsScreen();
            }
        });
        return btnPositions;
    }

    private static JButton getjButtonEmployees(JTextArea textArea1) {
        JButton btnPositions = new JButton("Employees");
        btnPositions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("Go to positions screen");
                new EmployeesScreen();
            }
        });
        return btnPositions;
    }

    private static JButton getjButtonDetails(JTextArea textArea1) {
        JButton btnPositions = new JButton("Details");
        btnPositions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("Go to positions screen");
                new DetailsScreen();
            }
        });
        return btnPositions;
    }

    private static JButton getjButtonStorage(JTextArea textArea1) {
        JButton btnPositions = new JButton("Storage");
        btnPositions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("Go to positions screen");
                new StorageScreen();
            }
        });
        return btnPositions;
    }

    private static JButton getjButtonOrder(JTextArea textArea1) {
        JButton btnPositions = new JButton("Orders");
        btnPositions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("Go to positions screen");
                new OrderScreen();
            }
        });
        return btnPositions;
    }

    public JPanel getMainPanel() {
        return JPanel;
    }

}

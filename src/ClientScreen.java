import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientScreen extends JFrame {
    private javax.swing.JPanel clntJPanel;
    private DefaultTableModel model;

    public ClientScreen() {

        JFrame clntFrame = new JFrame("Clients");
        clntFrame.setSize(900,700);
        clntFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clntFrame.setLayout(new BorderLayout());

        String[] columnNames = {"id","client_name","family_name","phone_number","car_model","car_number"};
        model = new DefaultTableModel(columnNames,0);
        DefaultTableModel addmodel = new DefaultTableModel(columnNames,0);
        JTable tableWData = new JTable(model);
        JTable addTable = new JTable(addmodel);

        JPanel dataPanel = new JPanel();
        dataPanel.setPreferredSize(new Dimension(450,350));
        dataPanel.setLayout(new BorderLayout());

        JScrollPane scrollPaneLeft = new JScrollPane(tableWData);
        dataPanel.add(scrollPaneLeft, BorderLayout.CENTER);
        loadDataToTable();

        JButton updButton = new JButton("Update data");
        dataPanel.add(updButton, BorderLayout.NORTH);
        updButton.addActionListener(e -> {
            model.setRowCount(0);
            loadDataToTable();
        });

        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.setPreferredSize(new Dimension(450,350));

        JScrollPane scrollPane = new JScrollPane(addTable);
        addPanel.add(scrollPane,BorderLayout.CENTER);

        JButton saveButton = new JButton("Save data");
        addPanel.add(saveButton, BorderLayout.NORTH);
        saveButton.addActionListener(e -> insertToDB());

        JButton backButton = new JButton("Back to main");
        addPanel.add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(e -> {
            clntFrame.dispose();
        });

        clntFrame.add(dataPanel,BorderLayout.WEST);
        clntFrame.add(addPanel, BorderLayout.CENTER);
        clntFrame.setVisible(true);

    }

    private void loadDataToTable() {
        List<String[]> data = DataBase.getClients();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void insertToDB() {
        JTextField nameField = new JTextField();
        JTextField fnameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField carModelField = new JTextField();
        JTextField carNumberField = new JTextField();

        Object[] message = {
                "Client name:", nameField,
                "Client family name:", fnameField,
                "Client phone:", phoneField,
                "Client car model:", carModelField,
                "Client car number:", carNumberField,
        };

        int option = JOptionPane.showConfirmDialog(this,message,"Add client",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String fname = fnameField.getText();
            String strPhone = phoneField.getText();
            int phone = Integer.parseInt(strPhone);
            String carModel = carModelField.getText();
            String carNumber = carNumberField.getText();

            if (!name.isEmpty() && !fname.isEmpty()) {
                DataBase.insertClientsToDB(name,fname,phone,carModel,carNumber);
                model.addRow(new String[]{name,fname,strPhone,carModel,carNumber});
            }
            else JOptionPane.showMessageDialog(this,"Error");
        }
    }

}

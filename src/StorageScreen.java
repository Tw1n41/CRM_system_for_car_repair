import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StorageScreen extends JFrame {

    private javax.swing.JPanel posJPanel;
    private DefaultTableModel model;

    public StorageScreen() {

        JFrame pstnsFrame = new JFrame("Employees");
        pstnsFrame.setSize(900,700);
        pstnsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pstnsFrame.setLayout(new BorderLayout());

        String[] columnNames = {"id","det_id","name","emp_id","emp_name","emp_fname","q"};
        model = new DefaultTableModel(columnNames,0);
        JTable tableWData = new JTable(model);

        JPanel dataPanel = new JPanel();
        dataPanel.setPreferredSize(new Dimension(650,350));
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
        addPanel.setPreferredSize(new Dimension(250,350));

        JButton saveButton = new JButton("Save data");
        addPanel.add(saveButton, BorderLayout.NORTH);
        saveButton.addActionListener(e -> insertToDB());

        JButton backButton = new JButton("Back to main");
        addPanel.add(backButton, BorderLayout.SOUTH);
        backButton.addActionListener(e -> {
            pstnsFrame.dispose();
        });

        pstnsFrame.add(dataPanel,BorderLayout.WEST);
        pstnsFrame.add(addPanel, BorderLayout.CENTER);
        pstnsFrame.setVisible(true);

    }

    private void loadDataToTable() {
        List<String[]> data = DataBase.getStorage();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void insertToDB() {
        JTextField detId = new JTextField();
        JTextField empId = new JTextField();
        JTextField q = new JTextField();

        Object[] message = {
                "Detail id:", detId,
                "Responsible for detail employee id:", empId,
                "Quantity:", q
        };

        int option = JOptionPane.showConfirmDialog(this,message,"Add position",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String strId = detId.getText();
            int dId = Integer.parseInt(strId);
            String strEId = empId.getText();
            int eId = Integer.parseInt(strEId);
            String strQ = q.getText();
            int intQ = Integer.parseInt(strQ);

            if (dId > 0 && eId > 0 && intQ > 0) {
                DataBase.insertStorageToDB(dId,eId,intQ);
                model.addRow(new String[]{strId,strEId,strQ});
            }
            else JOptionPane.showMessageDialog(this,"Error");
        }
    }

}

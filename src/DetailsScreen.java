import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DetailsScreen extends JFrame {

    private javax.swing.JPanel posJPanel;
    private DefaultTableModel model;

    public DetailsScreen() {

        JFrame pstnsFrame = new JFrame("Employees");
        pstnsFrame.setSize(900,700);
        pstnsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pstnsFrame.setLayout(new BorderLayout());

        String[] columnNames = {"id","emp_id","emp_name","pos_name","name","cost"};
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
        List<String[]> data = DataBase.getDetails();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void insertToDB() {
        JTextField empId = new JTextField();
        JTextField name = new JTextField();
        JTextField cost = new JTextField();

        Object[] message = {
                "Responsible for detail employee id:", empId,
                "Detail name:", name,
                "Detail cost:", cost
        };

        int option = JOptionPane.showConfirmDialog(this,message,"Add position",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String strId = empId.getText();
            int eId = Integer.parseInt(strId);
            String strName = name.getText();
            String strCost = cost.getText();
            double dCost = Double.parseDouble(strCost);

            if (!strName.isEmpty() && dCost > 0) {
                DataBase.insertDetailsToDB(eId,strName,dCost);
                model.addRow(new String[]{strId,strName,strCost});
            }
            else JOptionPane.showMessageDialog(this,"Error");
        }
    }

}

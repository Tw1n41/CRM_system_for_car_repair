import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeesScreen extends JFrame {

    private javax.swing.JPanel posJPanel;
    private DefaultTableModel model;

    public EmployeesScreen() {

        JFrame pstnsFrame = new JFrame("Employees");
        pstnsFrame.setSize(900,700);
        pstnsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pstnsFrame.setLayout(new BorderLayout());

        String[] columnNames = {"emp_id","pos_id","pos_name","emp_name","emp_fname","emp_phone"};
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
        List<String[]> data = DataBase.getEmployees();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void insertToDB() {
        JTextField posId = new JTextField();
        JTextField empName = new JTextField();
        JTextField empFname = new JTextField();
        JTextField empPhone = new JTextField();

        Object[] message = {
                "Position id:", posId,
                "Employee name:", empName,
                "Employee family name:", empFname,
                "Employee phone:", empPhone,
        };

        int option = JOptionPane.showConfirmDialog(this,message,"Add position",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String strId = posId.getText();
            int pId = Integer.parseInt(strId);
            String strName = empName.getText();
            String strFname = empFname.getText();
            String strPhone = empPhone.getText();
            int phone = Integer.parseInt(strPhone);

            if (!strName.isEmpty() && pId > 0) {
                DataBase.insertEmployeesToDB(pId,strName,strFname,phone);
                model.addRow(new String[]{strId,strName,strFname,strPhone});
            }
            else JOptionPane.showMessageDialog(this,"Error");
        }
    }

}

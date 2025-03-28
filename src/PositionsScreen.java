import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PositionsScreen extends JFrame {

    private javax.swing.JPanel posJPanel;
    private DefaultTableModel model;

    public PositionsScreen() {

        JFrame pstnsFrame = new JFrame("Positions");
        pstnsFrame.setSize(900,700);
        pstnsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pstnsFrame.setLayout(new BorderLayout());

        String[] columnNames = {"id","pos_name","coeff","min_sum","salary"};
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

        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));

        JButton saveButton = new JButton("Save data");
        addPanel.add(saveButton); //, BorderLayout.NORTH
        saveButton.addActionListener(e -> insertToDB());

        addPanel.add(Box.createVerticalStrut(10));

        JButton mrotButton = new JButton("Update MROT");
        addPanel.add(mrotButton); //BorderLayout.AFTER_LAST_LINE
        mrotButton.addActionListener(e -> insertMrotToDB());

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
        List<String[]> data = DataBase.getPositions();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void insertToDB() {
        JTextField posField = new JTextField();
        JTextField coeffField = new JTextField();

        Object[] message = {
                "Position name:", posField,
                "MROT coeff:", coeffField
        };

        int option = JOptionPane.showConfirmDialog(this,message,"Add position",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String posName = posField.getText();
            String strCoeff = coeffField.getText();
            int coeff = Integer.parseInt(strCoeff);

            if (!posName.isEmpty() && coeff > 0) {
                DataBase.insertPositionToDB(posName,coeff);
                model.addRow(new String[]{posName,strCoeff});
            }
            else JOptionPane.showMessageDialog(this,"Error");
        }
    }

    private void insertMrotToDB() {
        JTextField mrotField = new JTextField();

        Object[] message = {
                "New MROT:", mrotField
        };

        int option = JOptionPane.showConfirmDialog(this,message,"Add client",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String strMrot = mrotField.getText();
            int mrot = Integer.parseInt(strMrot);

            if (mrot > 0) {
                DataBase.changeMrot(mrot);
                model.addRow(new String[]{strMrot});
            }
            else JOptionPane.showMessageDialog(this,"Error");
        }
    }

}

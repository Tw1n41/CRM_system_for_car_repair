import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class OrderScreen extends JFrame {

    private javax.swing.JPanel clntJPanel;
    private DefaultTableModel model;

    public OrderScreen() {

        JFrame clntFrame = new JFrame("Clients");
        clntFrame.setSize(900,700);
        clntFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clntFrame.setLayout(new BorderLayout());

        String[] columnNames = {"id","client_id","client_name","client_fname","car_number","manager_id","manager_name",
                "manager_fname","master_id","detail_id","name","q","paid","date"};
        model = new DefaultTableModel(columnNames,0);
        JTable tableWData = new JTable(model);

        JPanel dataPanel = new JPanel();
        dataPanel.setPreferredSize(new Dimension(750,350));
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
        addPanel.setPreferredSize(new Dimension(150,350));

        JButton saveButton = new JButton("Save data");
        addPanel.add(saveButton, BorderLayout.NORTH);
        saveButton.addActionListener(e -> insertToDB());

        JButton btnAnalytic = getBtnAnalytics(tableWData);
        addPanel.add(btnAnalytic, BorderLayout.CENTER);

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
        List<String[]> data = DataBase.getOrder();
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void insertToDB() {
        JTextField id = new JTextField();
        JTextField clId = new JTextField();
        JTextField mngId = new JTextField();
        JTextField masterId = new JTextField();

        JSpinner date = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(date, "dd.MM.yyyy");
        date.setEditor(editor);

        JTextField detId = new JTextField();
        JTextField q = new JTextField();

        Object[] message = {
                "Order id:", id,
                "Client id:", clId,
                "Manager id:", mngId,
                "master id:", masterId,
                "Date:", date,
                "Detail id:", detId,
                "Quantity:", q
        };

        int option = JOptionPane.showConfirmDialog(this,message,"Add client",JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String strId = id.getText();
            int intId = Integer.parseInt(strId);
            String strCl = clId.getText();
            int intCl = Integer.parseInt(strCl);
            String strMng = mngId.getText();
            int intMng = Integer.parseInt(strMng);
            String strMaster = masterId.getText();
            int intMaster = Integer.parseInt(strMaster);
            Date dDate = (Date) date.getValue();
            java.sql.Date sDate = new java.sql.Date(dDate.getTime());
            String strDet = detId.getText();
            int intDet = Integer.parseInt(strDet);
            String strQ = q.getText();
            int intQ = Integer.parseInt(strQ);

            if (intId > 0 && intCl > 0 && intMaster > 0 && intMng > 0 && intQ > 0 && intDet > 0) {
                DataBase.insertOrderToDB(intId,intCl,intMng,intMaster,sDate,intDet,intQ);
                model.addRow(new String[]{strId,strCl,strMng,strMaster,dDate.toString(),strDet,strQ});
            }
            else JOptionPane.showMessageDialog(this,"Error");
        }
    }

    private static JButton getBtnAnalytics(JTable tableWithData) {
        JButton btnAnalytic = new JButton("Analytics");
        btnAnalytic.addActionListener(e -> new AnalyticScreen(tableWithData));
        return btnAnalytic;
    }
}

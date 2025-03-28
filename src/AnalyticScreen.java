import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AnalyticScreen extends JFrame {
    private JTable orders;
    private JTextArea area;

    public AnalyticScreen(JTable orderTable) {
        this.orders = orderTable;

        JFrame analyticFrame = new JFrame("Analytics");
        analyticFrame.setSize(900,700);
        analyticFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        analyticFrame.setLayout(new BorderLayout());

        area = new JTextArea();
        area.setEditable(false);

        JScrollPane sp = new JScrollPane(area);

        JButton btnRefr = getRefrBtn();

        analyticFrame.add(sp, BorderLayout.CENTER);
        analyticFrame.add(btnRefr, BorderLayout.SOUTH);
        analyticFrame.setVisible(true);
    }

    private JButton getRefrBtn() {
        JButton btnRefr = new JButton("Refresh data");
        btnRefr.addActionListener(e -> loadAnalytics());
        return btnRefr;
    }

    private void loadAnalytics() {

        StringBuilder rep = new StringBuilder();

        DefaultTableModel model = (DefaultTableModel) orders.getModel();
        int rowCount = model.getRowCount();

        rep.append("General orders analytic: \n General orders count = ").append(rowCount).append("\n\n");

        rep.append("Managers orders:\n");
        appendStat(rep, getOrdersByCol("manager_id"));

        rep.append("Detail orders:\n");
        appendStat(rep,getOrdersByCol("detail_id"));

        area.setText(rep.toString());
    }

    private Map<String, Integer> getOrdersByCol(String col) {

        Map<String, Integer> stat = new HashMap<>();
        DefaultTableModel model = (DefaultTableModel) orders.getModel();
        int index = getColIndex(col);

        if (index == -1) return stat;

        for (int i = 0; i < model.getRowCount(); i++){
            String value = model.getValueAt(i, index).toString();
            stat.put(value, stat.getOrDefault(value,0)+1);
        }
        return stat;
    }

    private int getColIndex(String col) {
        DefaultTableModel model = (DefaultTableModel) orders.getModel();

        for (int i = 0; i < model.getColumnCount(); i++) {
            if (model.getColumnName(i).equalsIgnoreCase(col)) return i;
        }
        return -1;
    }

    private void appendStat(StringBuilder rep, Map<String, Integer> stat){
        for (Map.Entry<String,Integer> entry : stat.entrySet())
            rep.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }

}

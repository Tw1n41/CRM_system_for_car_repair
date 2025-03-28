import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class DataBase {

    private static final String Link = "jdbc:postgresql://localhost:5432/mini_CRM";
    private static final String User = "postgres";
    private static final String Password = "Own3(rR";

    public static Connection connect() {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(Link, User, Password);
            System.out.println("Connected successful");
        }
        catch (SQLException ex){
            System.out.println("Connection error");
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Class error");
            ex.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection conn) {
        try{
            conn.close();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static List<String[]> getClients() {
        List<String[]> clntsData = new ArrayList<>();
        String query = "SELECT *" +
                "FROM clients";

        try (Connection conn = connect();
             Statement stmnt = conn.createStatement();
             ResultSet resSet = stmnt.executeQuery(query)) {

            while (resSet.next()) {
                clntsData.add(new String[]{
                        resSet.getString("id"),
                        resSet.getString("client_name"),
                        resSet.getString("family_name"),
                        resSet.getString("phone_number"),
                        resSet.getString("car_model"),
                        resSet.getString("car_number")
                });
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return clntsData;
    }

    public static List<String[]> getPositions() {
        List<String[]> posData = new ArrayList<>();
        String query = "SELECT position.id, position.pos_name, position.coeff, mrot.min_sum, position.coeff * mrot.min_sum as salary " +
                "FROM position " +
                "CROSS JOIN mrot";

        try (Connection conn = connect();
             Statement stmnt = conn.createStatement();
             ResultSet resSet = stmnt.executeQuery(query)) {

            while (resSet.next()) {
                posData.add(new String[]{
                        resSet.getString("id"),
                        resSet.getString("pos_name"),
                        resSet.getString("coeff"),
                        resSet.getString("min_sum"),
                        resSet.getString("salary")
                });
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posData;
    }

    public static List<String[]> getEmployees() {
        List<String[]> posData = new ArrayList<>();
        String query = "SELECT employees.emp_id, employees.pos_id, position.pos_name, employees.emp_name, employees.emp_fname, employees.emp_phone" +
                " FROM employees " +
                "LEFT JOIN position " +
                "ON employees.pos_id = position.id";

        try (Connection conn = connect();
             Statement stmnt = conn.createStatement();
             ResultSet resSet = stmnt.executeQuery(query)) {

            while (resSet.next()) {
                posData.add(new String[]{
                        resSet.getString("emp_id"),
                        resSet.getString("pos_id"),
                        resSet.getString("pos_name"),
                        resSet.getString("emp_name"),
                        resSet.getString("emp_fname"),
                        resSet.getString("emp_phone")
                });
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posData;
    }

    public static List<String[]> getDetails() {
        List<String[]> posData = new ArrayList<>();
        String query = "SELECT detail.id, detail.emp_id, employees.emp_name, position.pos_name, detail.name, detail.cost " +
                "FROM detail " +
                "LEFT JOIN employees " +
                "ON detail.emp_id = employees.emp_id " +
                "LEFT JOIN position " +
                "ON employees.pos_id = position.id";

        try (Connection conn = connect();
             Statement stmnt = conn.createStatement();
             ResultSet resSet = stmnt.executeQuery(query)) {

            while (resSet.next()) {
                posData.add(new String[]{
                        resSet.getString("id"),
                        resSet.getString("emp_id"),
                        resSet.getString("emp_name"),
                        resSet.getString("pos_name"),
                        resSet.getString("name"),
                        resSet.getString("cost")
                });
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posData;
    }

    public static List<String[]> getStorage() {
        List<String[]> posData = new ArrayList<>();
        String query = "SELECT storage.id, storage.det_id, detail.name, storage.emp_id, employees.emp_name, employees.emp_fname, storage.q " +
                "FROM storage " +
                "LEFT JOIN detail " +
                "ON detail.id = storage.det_id " +
                "LEFT JOIN employees " +
                "ON storage.emp_id = employees.emp_id";

        try (Connection conn = connect();
             Statement stmnt = conn.createStatement();
             ResultSet resSet = stmnt.executeQuery(query)) {

            while (resSet.next()) {
                posData.add(new String[]{
                        resSet.getString("id"),
                        resSet.getString("det_id"),
                        resSet.getString("name"),
                        resSet.getString("emp_id"),
                        resSet.getString("emp_name"),
                        resSet.getString("emp_fname"),
                        resSet.getString("q")
                });
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posData;
    }

    public static List<String[]> getOrder() {
        List<String[]> posData = new ArrayList<>();
        String query = "SELECT cl_order.id, cl_order.client_id, clients.client_name as client_name, " +
                "clients.family_name as client_fname, clients.car_number, cl_order.manag_id, " +
                "employees.emp_name as manager_name, employees.emp_fname as manager_fname, " +
                "cl_order.master_id, cl_order.detail_id, detail.name, " +
                "cl_order.q, detail.cost * cl_order.q as paid, " +
                "cl_order.date " +
                "FROM cl_order " +
                "LEFT JOIN clients " +
                "ON cl_order.client_id = clients.id " +
                "LEFT JOIN employees " +
                "ON cl_order.manag_id = employees.emp_id " +
                "AND employees.pos_id = 2 " +
                "LEFT JOIN detail " +
                "ON cl_order.detail_id = detail.id";

        try (Connection conn = connect();
             Statement stmnt = conn.createStatement();
             ResultSet resSet = stmnt.executeQuery(query)) {

            while (resSet.next()) {
                posData.add(new String[]{
                        resSet.getString("id"),
                        resSet.getString("client_id"),
                        resSet.getString("client_name"),
                        resSet.getString("client_fname"),
                        resSet.getString("car_number"),
                        resSet.getString("manag_id"),
                        resSet.getString("manager_name"),
                        resSet.getString("manager_fname"),
                        resSet.getString("master_id"),
                        resSet.getString("detail_id"),
                        resSet.getString("name"),
                        resSet.getString("q"),
                        resSet.getString("paid")
                });
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return posData;
    }

    public static void insertClientsToDB(String name, String fname, int phone, String carModel, String carNumber) {

        String query = "INSERT INTO clients (client_name, family_name, phone_number, car_model, car_number) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement prpstmnt = conn.prepareStatement(query)){

            prpstmnt.setString(1,name);
            prpstmnt.setString(2,fname);
            prpstmnt.setInt(3,phone);
            prpstmnt.setString(4,carModel);
            prpstmnt.setString(5,carNumber);

            int affectedRows = prpstmnt.executeUpdate();
            if (affectedRows > 0) System.out.println("Successful update data");
            else System.out.println("Update data error");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertPositionToDB(String posName,int coeff) {

        String query = "INSERT INTO position (pos_name, coeff) VALUES (?, ?)";

        try (Connection conn = connect();
             PreparedStatement prpstmnt = conn.prepareStatement(query)){

            prpstmnt.setString(1,posName);
            prpstmnt.setInt(2,coeff);

            int affectedRows = prpstmnt.executeUpdate();
            if (affectedRows > 0) System.out.println("Successful update data");
            else System.out.println("Update data error");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertEmployeesToDB(int posId,String empName,String empFname,int empPhone) {

        String query = "INSERT INTO employees (pos_id, emp_name, emp_fname, emp_phone) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement prpstmnt = conn.prepareStatement(query)){

            prpstmnt.setInt(1,posId);
            prpstmnt.setString(2,empName);
            prpstmnt.setString(3,empFname);
            prpstmnt.setInt(4,empPhone);

            int affectedRows = prpstmnt.executeUpdate();
            if (affectedRows > 0) System.out.println("Successful update data");
            else System.out.println("Update data error");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertDetailsToDB(int empId,String name,double cost) {

        String query = "INSERT INTO detail (emp_id, name, cost) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement prpstmnt = conn.prepareStatement(query)){

            prpstmnt.setInt(1,empId);
            prpstmnt.setString(2,name);
            prpstmnt.setDouble(3,cost);

            int affectedRows = prpstmnt.executeUpdate();
            if (affectedRows > 0) System.out.println("Successful update data");
            else System.out.println("Update data error");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertStorageToDB(int detId,int empId,int q) {

        String query = "INSERT INTO storage (det_id, emp_id, q) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement prpstmnt = conn.prepareStatement(query)){

            prpstmnt.setInt(1,detId);
            prpstmnt.setInt(2,empId);
            prpstmnt.setInt(3,q);

            int affectedRows = prpstmnt.executeUpdate();
            if (affectedRows > 0) System.out.println("Successful update data");
            else System.out.println("Update data error");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void insertOrderToDB(int id, int clId, int mngId, int masterId, Date date, int detId, int q) {

        String query = "INSERT INTO cl_order (id, client_id, manag_id, master_id, date, detail_id, q) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement prpstmnt = conn.prepareStatement(query)){

            prpstmnt.setInt(1,id);
            prpstmnt.setInt(2,clId);
            prpstmnt.setInt(3,mngId);
            prpstmnt.setInt(4,masterId);
            prpstmnt.setDate(5, (java.sql.Date) date);
            prpstmnt.setInt(6,detId);
            prpstmnt.setInt(7,q);

            int affectedRows = prpstmnt.executeUpdate();
            if (affectedRows > 0) System.out.println("Successful update data");
            else System.out.println("Update data error");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void changeMrot(int mrot) {

        String query = "UPDATE mrot SET min_sum = ?";

        try (Connection conn = connect();
             PreparedStatement prpstmnt = conn.prepareStatement(query)){

            prpstmnt.setInt(1,mrot);

            int affectedRows = prpstmnt.executeUpdate();
            if (affectedRows > 0) System.out.println("Successful update data");
            else System.out.println("Update data error");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//    public static Vector<Vector<Object>> loadAllData(){
//        String query = "SELECT cl_order.id, clients.client_name as client_name, " +
//                "clients.family_name as client_fname, clients.car_number, " +
//                "employees.emp_name as manager_name, " +
//                "detail.name, " +
//                "cl_order.date " +
//                "FROM cl_order " +
//                "LEFT JOIN clients " +
//                "ON cl_order.client_id = clients.id " +
//                "LEFT JOIN employees " +
//                "ON cl_order.manag_id = employees.emp_id " +
//                "AND employees.pos_id = 2 " +
//                "LEFT JOIN detail " +
//                "ON cl_order.detail_id = detail.id";
//        return loadDataToVector(query,null);
//    }
//
//    public static Vector<Vector<Object>> filterOrders(String col, String value) {
//        String query = "SELECT cl_order.id, clients.client_name as client_name, " +
//                "clients.family_name as client_fname, clients.car_number, " +
//                "employees.emp_name as manager_name, " +
//                "detail.name, " +
//                "cl_order.date " +
//                "FROM cl_order " +
//                "LEFT JOIN clients " +
//                "ON cl_order.client_id = clients.id " +
//                "LEFT JOIN employees " +
//                "ON cl_order.manag_id = employees.emp_id " +
//                "AND employees.pos_id = 2 " +
//                "LEFT JOIN detail " +
//                "ON cl_order.detail_id = detail.id " +
//                "WHERE LOWER("+ col + ") LIKE LOWER(?)";
//        return loadDataToVector(query,"%" + value + "%");
//    }
//
//    private static Vector<Vector<Object>> loadDataToVector(String query, String value) {
//        Vector<Vector<Object>> dataVector = new Vector<>();
//        try (Connection conn = connect();
//             PreparedStatement stmnt = conn.prepareStatement(query)) {
//
//            if (value != null) stmnt.setString(1, value);
//
//            ResultSet resSet = stmnt.executeQuery();
//            ResultSetMetaData md = resSet.getMetaData();
//
//            while (resSet.next()) {
//                Vector<Object> row = new Vector<>();
//                for (int i=1;i<= md.getColumnCount();i++) row.add(resSet.getObject(i));
//                dataVector.add(row);
//            }
//        }
//        catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return dataVector;
//    }
//
//    public static void addOrder(String clName, String clFname, String carNum, String managName, String detName, Date date) {
//        String query = "INSERT INTO cl_order()";
//    }

}

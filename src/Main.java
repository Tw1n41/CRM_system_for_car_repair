
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        Connection conn = DataBase.connect();
        if (conn != null){
            System.out.println("Connection");
            DataBase.closeConnection(conn);
        }

        new AuthScreen();


    }
}
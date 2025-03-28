import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Authorization {

    private static final String Users = "users.txt";
    private static Map<String, String> userMap = new HashMap<>();

    static {loadUsers();}

    private static void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(Users))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] logPass = line.split(":");
                if (logPass.length == 2) userMap.put(logPass[0],logPass[1]);
            }
        }
        catch (IOException ex) {
            System.out.println("Can't found users file.");
        }
    }

    private static void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Users))){
            for (Map.Entry<String, String> entry : userMap.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String hashPass(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte i : digest) sb.append(String.format("%02x",i));
            return sb.toString();
        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isRegister(String user, String pass) {
        if (userMap.containsKey(user)) {
            return false;
        }
        userMap.put(user, hashPass(pass));
        saveUsers();
        return true;
    }

    public static boolean auth(String user, String pass){
        return userMap.containsKey(user) && userMap.get(user).equals(hashPass(pass));
    }
}

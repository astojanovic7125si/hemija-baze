package hemija.app;

import java.io.*;

public class UserFileManager {
    private static final String FILE_PATH = "src/main/resources/user.txt";

    public static boolean usernameExists(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username)) return true;
            }
        } catch (IOException e) { /* fajl prazan */ }
        return false;
    }

    // Vraca id ako je login ok, -1 ako nije
    public static int login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username) && parts[1].equals(password))
                    return Integer.parseInt(parts[2]);
            }
        } catch (IOException e) { /* fajl prazan */ }
        return -1;
    }

    public static void register(String username, String password, int id) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(username + ":" + password + ":" + id);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

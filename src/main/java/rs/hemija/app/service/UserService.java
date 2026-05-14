package rs.hemija.app.service;

import rs.hemija.app.model.User;

import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class UserService {

    private static final String FILE_PATH = "src/main/resources/users.txt";

    public static List<User> loadUsers(){

        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;

                String[] parts = line.split(";", -1);

                String username = parts[0];
                String password = parts[1];
                String role = parts[2];
                Integer idIstrazivac = null;

                if (parts.length > 3 && !parts[3].isEmpty()) {
                    idIstrazivac = Integer.parseInt(parts[3]);
                }

                users.add(new User(username, password, role, idIstrazivac));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
    public static User login(String username, String password){

        List<User> users = loadUsers();

        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        return null;
    }

    public static boolean usernameExists(String username){

        List<User> users = loadUsers();

        for (User user : users) {
            if (user.username.equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean registerUser(User newUser){

        if(usernameExists(newUser.username)){
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {

            bw.write(formatUser(newUser));
            bw.newLine();

            return true;

        } catch (IOException e) {

            e.printStackTrace();

            return false;
        }
    }

    public static boolean updateUser(String oldUsername, String newUsername, String newPassword){

        List<User> users = loadUsers();

        for (User user : users) {
            if(!user.username.equals(oldUsername) && user.username.equals(newUsername)){
                return false;
            }
        }
        boolean found = false;

        for (User user : users){

            if(user.username.equals(oldUsername)){

                user.username = newUsername;
                user.password = newPassword;
                found = true;
                break;
            }
        }
        if(!found){
            return false;
        }
        return saveUsers(users);
    }

    public static boolean deleteUser(String username, String password){

        List<User> users = loadUsers();

        boolean removed = users.removeIf(user ->
                user.username.equals(username) && user.password.equals(password));

        if(!removed){
            return false;
        }

        return saveUsers(users);
    }

    private static boolean saveUsers(List<User> users){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))){

            for(User user : users){
                bw.write(formatUser(user));
                bw.newLine();
            }

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private static String formatUser(User user){

        String id = "";

        if (user.idIstrazivac != null) {
            id = String.valueOf(user.idIstrazivac);
        }

        return user.username + ";" + user.password + ";" + user.role + ";" + id;

    }
}

package rs.hemija.app;

import rs.hemija.app.db.Db;
import rs.hemija.app.ui.LoginFrame;

import java.sql.Connection;

public class Main {

    public static void main(String[] args){
        try (Connection conn = Db.getConnection()){

            System.out.println("Connected");

        } catch (Exception e) {

            System.out.println("Connection error");
            e.printStackTrace();
        }

        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
}

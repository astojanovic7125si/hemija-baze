package model;

import java.sql.Connection;
import java.sql.*;

public class IzvodjenjeDto {

    public static void updateStatus(Connection connection, int idIzvodjenje, String noviStatus, int idIstrazivac) {
        String query =
                "UPDATE izvodjenje iv " +
                "JOIN izvodjac iz " +
                "ON iv.id_izvodjenje = iz.id_izvodjenje " +
                "SET iv.status = ? " +
                "WHERE iv.id_izvodjenje = ? " +
                "AND iz.id_istrazivac = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, noviStatus);
            ps.setInt(2, idIzvodjenje);
            ps.setInt(3, idIstrazivac);
            int rows = ps.executeUpdate();
            if(rows == 0){
                throw new RuntimeException("Nije pronađeno izvođenje.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

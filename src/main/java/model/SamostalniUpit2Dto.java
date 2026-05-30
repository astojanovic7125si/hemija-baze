package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SamostalniUpit2Dto {

    public static List<SamostalniUpit2Dto> readAll(Connection connection) {
        String query =
                "SELECT " +
                        "l.id_lab, " +
                        "l.naziv AS naziv_laboratorije, " +
                        "l.lokacija, " +
                        "COUNT(s.id_sesija) AS broj_sesija " +
                        "FROM laboratorija l " +
                        "LEFT JOIN izvodjenje iv ON l.id_lab = iv.id_lab " +
                        "LEFT JOIN sesija s ON iv.id_izvodjenje = s.id_izvodjenje " +
                        "GROUP BY l.id_lab, l.naziv, l.lokacija " +
                        "HAVING COUNT(s.id_sesija) >= 1 " +
                        "ORDER BY broj_sesija DESC";

        List<SamostalniUpit2Dto> lista = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                lista.add(new SamostalniUpit2Dto(
                        rs.getInt("id_lab"),
                        rs.getString("naziv_laboratorije"),
                        rs.getString("lokacija"),
                        rs.getInt("broj_sesija")
                ));
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final int idLab;
    private final String nazivLaboratorije;
    private final String lokacija;
    private final int brojSesija;

    public SamostalniUpit2Dto(int idLab, String nazivLaboratorije, String lokacija, int brojSesija) {
        this.idLab = idLab;
        this.nazivLaboratorije = nazivLaboratorije;
        this.lokacija = lokacija;
        this.brojSesija = brojSesija;
    }

    public int getIdLab() {
        return idLab;
    }

    public String getNazivLaboratorije() {
        return nazivLaboratorije;
    }

    public String getLokacija() {
        return lokacija;
    }

    public int getBrojSesija() {
        return brojSesija;
    }
}
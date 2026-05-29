package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SamostalniUpit1Dto {

    public static List<SamostalniUpit1Dto> readAll(Connection connection) {
        String query =
                "SELECT " +
                        "i.id_istrazivac, " +
                        "i.ime, " +
                        "i.prezime, " +
                        "COUNT(iv.id_izvodjenje) AS broj_uspesnih_eksperimenata " +
                        "FROM istrazivac i " +
                        "JOIN izvodjac iz " +
                        "ON i.id_istrazivac = iz.id_istrazivac " +
                        "JOIN izvodjenje iv " +
                        "ON iz.id_izvodjenje = iv.id_izvodjenje " +
                        "WHERE iv.status = 'zavrseno_uspesno' " +
                        "GROUP BY i.id_istrazivac, i.ime, i.prezime " +
                        "HAVING COUNT(iv.id_izvodjenje) >= 1";

        List<SamostalniUpit1Dto> lista = new ArrayList<>();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                SamostalniUpit1Dto dto = new SamostalniUpit1Dto(
                        rs.getInt("id_istrazivac"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getInt("broj_uspesnih_eksperimenata")
                );

                lista.add(dto);
            }

            return lista;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final int idIstrazivac;
    private final String ime;
    private final String prezime;
    private final int brojUspesnihEksperimenata;

    public SamostalniUpit1Dto(int idIstrazivac, String ime, String prezime, int brojUspesnihEksperimenata) {
        this.idIstrazivac = idIstrazivac;
        this.ime = ime;
        this.prezime = prezime;
        this.brojUspesnihEksperimenata = brojUspesnihEksperimenata;
    }

    public int getIdIstrazivac() {
        return idIstrazivac;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public int getBrojUspesnihEksperimenata() {
        return brojUspesnihEksperimenata;
    }
}
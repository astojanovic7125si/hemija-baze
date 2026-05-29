package model;

import java.sql.*;
import java.util.*;

public class SesijaDto {
    public static List<SesijaDto> readForIzvodjenje(Connection connection, int idIzvodjenje) {
        String query = "SELECT id_sesija, id_izvodjenje, datum, vreme_pocetka, vreme_zavrsetka " +
                "FROM sesija WHERE id_izvodjenje = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idIzvodjenje);
            ResultSet rs = ps.executeQuery();
            List<SesijaDto> lista = new ArrayList<>();
            while (rs.next()) {
                SesijaDto dto = new SesijaDto(
                        rs.getInt("id_sesija"),
                        rs.getInt("id_izvodjenje"),
                        rs.getString("datum"),
                        rs.getString("vreme_pocetka"),
                        rs.getString("vreme_zavrsetka")
                );
                lista.add(dto);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final int idSesija;
    private final int idIzvodjenje;
    private final String datum;
    private final String vremePocetka;
    private final String vremeZavrsetka;

    public SesijaDto(int idSesija, int idIzvodjenje, String datum,
                     String vremePocetka, String vremeZavrsetka) {
        this.idSesija = idSesija;
        this.idIzvodjenje = idIzvodjenje;
        this.datum = datum;
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
    }

    public int getIdSesija() { return idSesija; }
    public int getIdIzvodjenje() { return idIzvodjenje; }
    public String getDatum() { return datum; }
    public String getVremePocetka() { return vremePocetka; }
    public String getVremeZavrsetka() { return vremeZavrsetka; }
}

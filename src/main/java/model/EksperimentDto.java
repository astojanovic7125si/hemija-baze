package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EksperimentDto {
    public static List<EksperimentDto> readForIstrazivac(Connection connection, int idIstrazivac) {
        String query =
                "SELECT id_izvodjenje, " +
                        "naziv_eksperimenta, " +
                        "tip_eksperimenta, " +
                        "naziv_laboratorije, " +
                        "status_izvodjenja, " +
                        "broj_sesija " +
                        "FROM pregled_eksperimenata_istrazivaca " +
                        "WHERE id_istrazivac = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idIstrazivac);
            ResultSet rs = ps.executeQuery();
            List<EksperimentDto> lista = new ArrayList<>();
            while (rs.next()) {
                EksperimentDto dto = new EksperimentDto(
                        rs.getInt("id_izvodjenje"),
                        rs.getString("naziv_eksperimenta"),
                        rs.getString("tip_eksperimenta"),
                        rs.getString("naziv_laboratorije"),
                        rs.getString("status_izvodjenja"),
                        rs.getInt("broj_sesija")
                );
                lista.add(dto);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final int idIzvodjenje;
    private final String nazivEksperimenta;
    private final String tipEksperimenta;
    private final String nazivLaboratorije;
    private final String statusIzvodjenja;
    private final int brojSesija;

    public EksperimentDto(int idIzvodjenje, String nazivEksperimenta, String tipEksperimenta,
                          String nazivLaboratorije, String statusIzvodjenja, int brojSesija) {
        this.idIzvodjenje = idIzvodjenje;
        this.nazivEksperimenta = nazivEksperimenta;
        this.tipEksperimenta = tipEksperimenta;
        this.nazivLaboratorije = nazivLaboratorije;
        this.statusIzvodjenja = statusIzvodjenja;
        this.brojSesija = brojSesija;
    }
    public int getIdIzvodjenje() {return idIzvodjenje;}
    public String getNazivEksperimenta() { return nazivEksperimenta; }
    public String getTipEksperimenta() { return tipEksperimenta; }
    public String getNazivLaboratorije() { return nazivLaboratorije; }
    public String getStatusIzvodjenja() { return statusIzvodjenja; }
    public int getBrojSesija() { return brojSesija; }
}

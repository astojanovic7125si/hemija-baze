package rs.hemija.app.model;

public class User {

    public String username;
    public String password;
    public String role;
    public Integer idIstrazivac;

    public User(String username,
                String password,
                String role,
                Integer idIstrazivac){
        this.username = username;
        this.password = password;
        this.role = role;
        this.idIstrazivac = idIstrazivac;
    }
}

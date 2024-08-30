package zoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Paises {

    private int id;
    private String descr;
    private int idContinente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getIdContinente() {
        return idContinente;
    }

    public void setIdContinente(int idContinente) {
        this.idContinente = idContinente;
    }
    
     public List<Paises> getAllPaises() {
        List<Paises> paises = new ArrayList<>();
        String query = "SELECT * FROM paises";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paises pais = new Paises();
                pais.setId(rs.getInt("id"));
                pais.setDescr(rs.getString("descr"));
                pais.setIdContinente(rs.getInt("id_continente"));
                paises.add(pais);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paises;
    }

}

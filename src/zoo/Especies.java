package zoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Especies {
    
    private int id;
    private String descr;

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
    
    public List<Especies> getAllEspecies() {
        List<Especies> especies = new ArrayList<>();
        String query = "SELECT * FROM especies";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Especies especie = new Especies();
                especie.setId(rs.getInt("id"));
                especie.setDescr(rs.getString("descr"));
                especies.add(especie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return especies;
    }
    
}


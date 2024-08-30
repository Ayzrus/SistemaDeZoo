package zoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Continentes {

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

    public List<Continentes> getAllContinentes() {
        List<Continentes> continentes = new ArrayList<>();
        String query = "SELECT * FROM continentes";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Continentes continente = new Continentes();
                continente.setId(rs.getInt("id"));
                continente.setDescr(rs.getString("descr"));
                continentes.add(continente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return continentes;
    }

}

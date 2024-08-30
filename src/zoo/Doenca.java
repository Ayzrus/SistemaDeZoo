package zoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Doenca {

    private int id;
    private String descr;
    private int total_registros;

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

    public int getTotal_registros() {
        return total_registros;
    }

    public void setTotal_registros(int total_registros) {
        this.total_registros = total_registros;
    }

    public List<Doenca> getAllDoencas() {
        List<Doenca> doencas = new ArrayList<>();
        String query = "SELECT * FROM doenca";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doenca doenca = new Doenca();
                doenca.setId(rs.getInt("id"));
                doenca.setDescr(rs.getString("descr"));
                doencas.add(doenca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doencas;
    }

    public List<Doenca> ContagemDeDoencas() {
        List<Doenca> doencas = new ArrayList<>();
        String query = """
                       SELECT d.descr AS doenca, COUNT(rc.id) AS total_registros
                       FROM doenca d
                       LEFT JOIN resultado_consulta rc ON d.id = rc.id_doenca
                       LEFT JOIN consultas c ON rc.id_consulta = c.id
                       GROUP BY d.descr
                       ORDER BY d.descr ASC""";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Doenca doenca = new Doenca();
                doenca.setTotal_registros(rs.getInt("total_registros"));
                doenca.setDescr(rs.getString("doenca"));
                doencas.add(doenca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doencas;
    }

}

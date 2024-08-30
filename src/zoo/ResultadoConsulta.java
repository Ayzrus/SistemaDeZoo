package zoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultadoConsulta {

    private int id;
    private int idConsulta;
    private int idDoenca;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdDoenca() {
        return idDoenca;
    }

    public void setIdDoenca(int idDoenca) {
        this.idDoenca = idDoenca;
    }

    public List<ResultadoConsulta> getAllResultadosConsultas() {
        List<ResultadoConsulta> resultados = new ArrayList<>();
        String query = "SELECT * FROM resultado_consulta";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ResultadoConsulta resultado = new ResultadoConsulta();
                resultado.setId(rs.getInt("id"));
                resultado.setIdConsulta(rs.getInt("id_consulta"));
                resultado.setIdDoenca(rs.getInt("id_doenca"));
                resultados.add(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public void registrarConsulta( int IdConsulta, int IdDoenca) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO resultado_consulta (id_consulta, id_doenca) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, IdConsulta);
                preparedStatement.setInt(2, IdDoenca);
                
                preparedStatement.executeUpdate();
                System.out.println("Resultado Consulta registrada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar consulta: " + e.getMessage());
        }
    }

}

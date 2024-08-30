package zoo;

import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Consulta {

    private int id;
    private int idAnimal;
    private int idMedico;
    private double valor;
    private Date data;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Consulta> listarTodasConsultas() {
        List<Consulta> consultas = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM consultas";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Consulta consulta = new Consulta();
                    consulta.setId(resultSet.getInt("id"));
                    consulta.setIdAnimal(resultSet.getInt("id_animal"));
                    consulta.setIdMedico(resultSet.getInt("id_medico"));
                    consulta.setValor(resultSet.getDouble("valor"));
                    consulta.setData(resultSet.getDate("data"));
                    consultas.add(consulta);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar consultas: " + e.getMessage());
        }

        return consultas;
    }

    public void registrarConsulta(int idAnimal, int idMedico, double valor, Date data) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO consultas (id_animal, id_medico, valor, data) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, idAnimal);
                preparedStatement.setInt(2, idMedico);
                preparedStatement.setDouble(3, valor);
                preparedStatement.setDate(4, new java.sql.Date(data.getTime()));

                preparedStatement.executeUpdate();
                System.out.println("Consulta registrada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao registrar consulta: " + e.getMessage());
        }
    }

}

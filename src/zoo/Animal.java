package zoo;

import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Animal {

    private int nChip;
    private String nome;
    private int idEspecie;
    private Date dataNascimento;
    private int idPaisOrigem;
    private String Doenca;
    private String Especie;
    private String Pais;
    private String Continente;

    // Getters and setters
    public int getNChip() {
        return nChip;
    }

    public void setNChip(int nChip) {
        this.nChip = nChip;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(int idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public int getIdPaisOrigem() {
        return idPaisOrigem;
    }

    public void setIdPaisOrigem(int idPaisOrigem) {
        this.idPaisOrigem = idPaisOrigem;
    }

    public String getDoenca() {
        return Doenca;
    }

    public void setDoenca(String Doenca) {
        this.Doenca = Doenca;
    }

    public String getEspecie() {
        return Especie;
    }

    public void setEspecie(String Especie) {
        this.Especie = Especie;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String Pais) {
        this.Pais = Pais;
    }

    public String getContinente() {
        return Continente;
    }

    public void setContinente(String Continente) {
        this.Continente = Continente;
    }

    public List<Animal> getAllAnimais() {
        List<Animal> animais = new ArrayList<>();
        String query = "SELECT * FROM animais";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Animal animal = new Animal();
                animal.setNChip(rs.getInt("n_chip"));
                animal.setNome(rs.getString("nome"));
                animal.setIdEspecie(rs.getInt("id_especie"));
                animal.setDataNascimento(rs.getDate("Data_De_Nascimento"));
                animal.setIdPaisOrigem(rs.getInt("id_pais_origem"));
                animais.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animais;
    }

    public List<Animal> getAnimal(int N_Chip) {
        List<Animal> animais = new ArrayList<>();
        String query = """
                       SELECT A.*, D.Descr AS Doencas, E.Descr AS Especie, P.Descr AS Pais, CP.Descr AS Continente
                       FROM animais A
                       LEFT JOIN consultas C ON C.Id_Animal = A.N_Chip
                       LEFT JOIN resultado_consulta RC ON RC.Id_Consulta = C.Id
                       LEFT JOIN doenca D ON D.Id = RC.Id_Doenca
                       LEFT JOIN especies E ON E.Id = A.Id_Especie
                       LEFT JOIN Paises P ON P.Id = A.Id_Pais_Origem
                       LEFT JOIN continentes CP ON CP.Id = P.Id_Continente
                       WHERE N_Chip = """ + N_Chip;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Animal animal = new Animal();
                animal.setNChip(rs.getInt("n_chip"));
                animal.setNome(rs.getString("nome"));
                animal.setIdEspecie(rs.getInt("id_especie"));
                animal.setDataNascimento(rs.getDate("Data_De_Nascimento"));
                animal.setIdPaisOrigem(rs.getInt("id_pais_origem"));
                animal.setDoenca(rs.getString("Doencas"));
                animal.setEspecie(rs.getString("Especie"));
                animal.setPais(rs.getString("Pais"));
                animal.setContinente(rs.getString("Continente"));
                animais.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animais;
    }

    public void removeAnimal(int nChip) {
        String query = "DELETE FROM animais WHERE n_chip = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, nChip);
            stmt.executeUpdate();
            System.out.println("Animal Removido Com Sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarAnimal(Animal animal) {
        String query = "INSERT INTO animais (n_chip, nome, id_especie, Data_De_Nascimento, id_pais_origem) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, animal.getNChip());
            stmt.setString(2, animal.getNome());
            stmt.setInt(3, animal.getIdEspecie());
            stmt.setDate(4, new java.sql.Date(animal.getDataNascimento().getTime()));
            stmt.setInt(5, animal.getIdPaisOrigem());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserirConsulta(int idAnimal, int idMedico, double valor, Date data) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO consultas (id_animal, id_medico, valor, data) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, idAnimal);
                preparedStatement.setInt(2, idMedico);
                preparedStatement.setDouble(3, valor);
                preparedStatement.setDate(4, new java.sql.Date(data.getTime()));

                preparedStatement.executeUpdate();
                System.out.println("Consulta inserida com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir consulta: " + e.getMessage());
        }
    }

}

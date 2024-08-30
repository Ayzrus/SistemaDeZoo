package zoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Veterinario {

    private int nOrdem;
    private String nome;
    private String morada;
    private String contacto;
    private String email;
    private String password;

    public int getNOrdem() {
        return nOrdem;
    }

    public void setNOrdem(int nOrdem) {
        this.nOrdem = nOrdem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Veterinario> getAllVeterinarios() {
        List<Veterinario> veterinarios = new ArrayList<>();
        String query = "SELECT * FROM veterinario";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veterinario veterinario = new Veterinario();
                veterinario.setNOrdem(rs.getInt("n_ordem"));
                veterinario.setNome(rs.getString("nome"));
                veterinario.setMorada(rs.getString("morada"));
                veterinario.setContacto(rs.getString("contacto"));
                veterinario.setEmail(rs.getString("email"));
                veterinario.setEmail(rs.getString("password"));
                veterinarios.add(veterinario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veterinarios;
    }

    public boolean verificaEmailVet(String email) {
        String query = "SELECT COUNT(*) FROM veterinario WHERE Email = ?";
        boolean emailExists = false;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    emailExists = count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailExists;
    }

    public String getPasswordVet(String email) {
        String query = "SELECT Password FROM veterinario WHERE Email = ?";
        String flag = "";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    flag = rs.getString("Password");
                } else {
                    System.out.println("Email não existe");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    public void removeVeterinario(int nOrdem) {
        String query = "DELETE FROM veterinario WHERE n_ordem = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, nOrdem);
            stmt.executeUpdate();
            System.out.println("Veterinario Removido Com Sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registrarMedico(Veterinario medico) {
        String query = "INSERT INTO veterinario (n_ordem, nome, morada, contacto, email, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, medico.getNOrdem());
            stmt.setString(2, medico.getNome());
            stmt.setString(3, medico.getMorada());
            stmt.setString(4, medico.getContacto());
            stmt.setString(5, medico.getEmail());
            stmt.setString(6, medico.getPassword());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

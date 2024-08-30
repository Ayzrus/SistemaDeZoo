package zoo;
import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost/zoo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL não encontrado. Certifique-se de ter adicionado a biblioteca do MySQL ao seu projeto.");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados.");
        }
        return null;
    }
}

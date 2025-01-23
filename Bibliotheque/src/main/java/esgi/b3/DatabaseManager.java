package esgi.b3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    /**
     * URL de la base de données
     */
    private static final String URL = "jdbc:sqlite:bibliotheque-db.db";

    /**
     * Récupérer la connexion à la base de données
     * @return connexion
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}

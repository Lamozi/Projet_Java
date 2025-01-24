package esgi.b3;

import esgi.b3.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DataBaseMaintenace {

    // Initialisation du logger
    private static final Logger logger = LoggerFactory.getLogger(DataBaseMaintenace.class);
    /**
     * Méthode main pour la maintenance de la base de données
     * A ne pas lancer : car risque de perdre les données
     */
    public static void main(String[] args) throws SQLException {

        // URL pour une base de données SQLite en mémoire
        String url = "jdbc:sqlite:bibliotheque-db.db";
        // Déclaration de la connexion
        Connection connection = null;

        try {
            // Initialisation de la connexion
            connection = DriverManager.getConnection(url);
            logger.info("Connection à la base de données:'bibliotheque-db.db' établie avec succès.");
            // Ajouter la colonne "status"
            try (Statement statement = connection.createStatement()) {
                String alterTableSQL = "ALTER TABLE livres ADD COLUMN status TEXT NOT NULL DEFAULT 'disponible' CHECK (status IN ('disponible', 'emprunte'))";
                statement.execute(alterTableSQL);
                System.out.println("Colonne 'status' ajoutée à la table 'livres'");
            } catch (Exception e) {
                logger.error("Erreur lors de l'ajout de la colonne 'status' à la table 'livres' : ", e);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    logger.info("Connection à la base de données fermée avec succès.");
                }
            } catch (SQLException e) {
                logger.error("Erreur lors de la fermeture de la connexion à la base de données : ", e);
            }
        }
    }
}

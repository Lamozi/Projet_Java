package esgi.b3;

import java.sql.*;

public class DataBaseMaintenace {
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
            System.out.println("Connected to the database: bibliotheque-db.db");

            // Ajouter la colonne "status"
            try (Statement statement = connection.createStatement()) {
                String alterTableSQL = "ALTER TABLE livres ADD COLUMN status TEXT NOT NULL DEFAULT 'disponible' CHECK (status IN ('disponible', 'emprunte'))";
                statement.execute(alterTableSQL);
                System.out.println("Column 'status' added to 'livres' table");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }



        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Connection closed");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

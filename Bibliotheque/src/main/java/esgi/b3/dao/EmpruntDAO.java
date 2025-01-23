package esgi.b3.dao;

import esgi.b3.DatabaseManager;
import esgi.b3.models.Emprunt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {
    /**
     * Récupérer la liste des emprunts
     * @return liste des emprunts
     */
    public List<Emprunt> getAllEmprunts() throws SQLException {
        String query = "SELECT * FROM emprunts";
        List<Emprunt> emprunts = new ArrayList<>();
        try (Connection connection = DatabaseManager.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                emprunts.add(new Emprunt(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_livre"),
                        resultSet.getInt("id_user"),
                        resultSet.getDate("date_emprunt").toLocalDate(),
                        resultSet.getDate("date_retour").toLocalDate()
                ));
            }
        }
        return emprunts;
    }

    /**
     * Ajouter un emprunt
     * @param emprunt emprunt
     */
    public void addEmprunt(Emprunt emprunt) throws SQLException {
        String query = "INSERT INTO emprunts (id_livre, id_user, date_emprunt, date_retour) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, emprunt.getLivreId());
            preparedStatement.setInt(2, emprunt.getUserId());
            preparedStatement.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.executeUpdate();
        }
    }

    /**
     * Rendre un livre
     * @param emprunt emprunt
     */
    public void rendreLivre(Emprunt emprunt) throws SQLException {
        String query = "DELETE FROM emprunts WHERE id = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, emprunt.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupérer l'empreint par l'id du livre
     * @param id_livre id du livre
     * @return emprunt
     */
    public Emprunt getEmpruntByLivreId(int id_livre) throws SQLException {
        String query = "SELECT * FROM emprunts WHERE id_livre = ?";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id_livre);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Emprunt(
                            resultSet.getInt("id"),
                            resultSet.getInt("id_livre"),
                            resultSet.getInt("id_user"),
                            resultSet.getDate("date_emprunt").toLocalDate(),
                            resultSet.getDate("date_retour").toLocalDate()
                    );
                }
            }
        }
        return null;
    }
}

package esgi.b3.controllers;

import esgi.b3.models.Livre;
import esgi.b3.services.LivreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class LivreController {

    @FXML
    private ListView<String> listViewLivres;

    private final LivreService livreService = new LivreService();
    private final ObservableList<String> observableLivres = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Charger les livres depuis la base de données
        try {
            List<Livre> livres = livreService.getLivres();
            observableLivres.setAll(livres.stream().map(this::formatLivre).toList());
            listViewLivres.setItems(observableLivres);
        } catch (SQLException e) {
            showError("Erreur lors de la récupération des livres : " + e.getMessage());
        }
    }

    @FXML
    public void handleAjouterLivre() {
        try {
            // Ajouter un nouveau livre (par exemple avec des valeurs fictives ou via une fenêtre de saisie)
            livreService.ajouterLivre("Titre exemple", "Auteur exemple", "Genre exemple", "Disponible");

            // Mettre à jour la liste
            List<Livre> livres = livreService.getLivres();
            observableLivres.clear();
            for (Livre livre : livres) {
                observableLivres.add(formatLivre(livre));
            }
        } catch (SQLException e) {
            showError("Erreur lors de l'ajout du livre : " + e.getMessage());
        }
    }

    @FXML
    public void handleSupprimerLivre() {
        try {
            // Récupérer l'élément sélectionné
            String livreSelectionne = listViewLivres.getSelectionModel().getSelectedItem();
            if (livreSelectionne != null) {
                // Extraire l'ID du livre à partir de la chaîne
                int idLivre = extractIdFromFormattedLivre(livreSelectionne);

                // Supprimer le livre
                livreService.supprimerLivre(idLivre);

                // Mettre à jour la liste
                List<Livre> livres = livreService.getLivres();
                observableLivres.clear();
                for (Livre livre : livres) {
                    observableLivres.add(formatLivre(livre));
                }
            } else {
                showWarning("Aucun livre sélectionné", "Veuillez sélectionner un livre à supprimer.");
            }
        } catch (SQLException e) {
            showError("Erreur lors de la suppression du livre : " + e.getMessage());
        }
    }

    @FXML
    public void handleRetour() {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) listViewLivres.getScene().getWindow();
        stage.close();
    }

    private String formatLivre(Livre livre) {
        return livre.getId() + " - " + livre.getTitre() + " (" + livre.getAuteur() + ")";
    }

    private int extractIdFromFormattedLivre(String formattedLivre) {
        return Integer.parseInt(formattedLivre.split(" - ")[0]);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

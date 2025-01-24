package esgi.b3.controllers;

import esgi.b3.exports.ExportEnJson;
import esgi.b3.exports.ImportJson;
import esgi.b3.ui.EmpruntUI;
import esgi.b3.ui.LivreUI;
import esgi.b3.ui.UserUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.Optional;

public class MainController {

    private final LivreUI livreUI = new LivreUI();
    private final UserUI userUI = new UserUI();
    private final EmpruntUI empruntUI = new EmpruntUI();
    private final ExportEnJson exportEnJson = new ExportEnJson();
    private final ImportJson importJson = new ImportJson();

    // Méthode pour la gestion des livres
    @FXML
    public void handleGestionLivres() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LivreView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Gestion des Livres");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour la gestion des utilisateurs
    @FXML
    public void handleGestionUtilisateurs() {
        // Par exemple, afficher un message ou une fenêtre pour gérer les utilisateurs
        userUI.afficheMenuUsers();
    }

    // Méthode pour le suivi des emprunts
    @FXML
    public void handleSuiviEmprunts() {
        // Par exemple, afficher un message ou une fenêtre pour gérer les emprunts
        empruntUI.afficherMenuEmprunts();
    }

    // Méthode pour exporter en JSON
    @FXML
    public void handleExportJson() {
        // Lancer l'exportation de la base de données en JSON
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Exportation en JSON");
        alert.setHeaderText(null);
        alert.setContentText("Exportation de la base de données en JSON en cours...");
        alert.showAndWait();

        exportEnJson.exporterBaseDeDonneesEnJson();
    }

    // Méthode pour importer depuis un fichier JSON
    @FXML
    public void handleImportJson() {
        // Afficher une fenêtre pour demander à l'utilisateur de saisir le chemin du fichier JSON
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Importer JSON");
        dialog.setHeaderText("Veuillez entrer le chemin du fichier JSON à importer :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(filePath -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Importation en JSON");
            alert.setHeaderText(null);
            alert.setContentText("Importation du fichier JSON : " + filePath);
            alert.showAndWait();

            importJson.importerBaseDeDonneesDepuisJson(filePath);  // Appel de la méthode d'importation
        });
    }
}

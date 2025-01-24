package esgi.b3.exports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import esgi.b3.models.Emprunt;
import esgi.b3.models.Livre;
import esgi.b3.models.User;
import esgi.b3.services.EmpruntService;
import esgi.b3.services.LivreService;
import esgi.b3.services.UserService;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ExportEnJson {

    /** LivreService */
    private final LivreService livreService = new LivreService();
    /** UserService */
    private final UserService userService = new UserService();
    /** EmpruntService */
    private final EmpruntService empruntService = new EmpruntService();

    /**
     * Exporter la base de données en JSON
     */
    public void exporterBaseDeDonneesEnJson() {
        System.out.println("Exportation de la base de données en JSON...");

        try {
            // Récupération des données
            List<Livre> livres = livreService.getLivres();
            List<User> users = userService.getUsers();
            List<Emprunt> emprunts = empruntService.getEmprunts();

            // Préparer l'objet contenant toutes les données
            var data = Map.of(
                    "livres", livres,
                    "users", users,
                    "emprunts", emprunts
            );

            // Configurer Jackson avec une stratégie de nommage en snake_case
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Pour gérer LocalDate
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Indentation
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE); // Utilisation de snake_case

            // Obtenir le dossier où se trouve la classe ExportEnJson
            String directoryPath = new File(ExportEnJson.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParent();

            // Chemin complet du fichier JSON
            File jsonFile = new File(directoryPath, "export_bibliotheque.json");

            // Vérification et remplacement
            if (jsonFile.exists()) {
                System.out.println("Un fichier existant a été trouvé et sera remplacé : " + jsonFile.getAbsolutePath());
            }

            // Écrire dans le fichier (remplace automatiquement si le fichier existe)
            objectMapper.writeValue(jsonFile, data);
            System.out.println("Exportation réussie dans le fichier : " + jsonFile.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Erreur lors de l'exportation : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
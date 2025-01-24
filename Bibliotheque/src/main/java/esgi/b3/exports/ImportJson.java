package esgi.b3.exports;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import esgi.b3.models.Emprunt;
import esgi.b3.models.Livre;
import esgi.b3.models.User;
import esgi.b3.services.EmpruntService;
import esgi.b3.services.LivreService;
import esgi.b3.services.UserService;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ImportJson {
    /** Services */
    private final LivreService livreService = new LivreService();
    private final UserService userService = new UserService();
    private final EmpruntService empruntService = new EmpruntService();

    /**
     * Importer des données JSON dans la base de données
     * @param filePath Chemin du fichier JSON à importer
     */
    public void importerBaseDeDonneesDepuisJson(String filePath) {
        System.out.println("Importation des données depuis le fichier JSON...");

        try {
            // Configurer Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Pour gérer LocalDate

            // Lire les données du fichier JSON
            Map<String, List<?>> data = objectMapper.readValue(new File(filePath), Map.class);

            // Importer les livres
            List<Map<String, Object>> livres = (List<Map<String, Object>>) data.get("livres");
            if (livres != null) {
                for (Map<String, Object> livreData : livres) {
                    Livre livre = new Livre(
                            (Integer) livreData.get("id"),
                            (String) livreData.get("titre"),
                            (String) livreData.get("genre"),
                            (String) livreData.get("auteur"),
                            (String) livreData.get("status")
                    );
                    livreService.ajouterLivre(livre.getTitre(), livre.getAuteur(), livre.getGenre(), livre.getStatus().toString());
                }
                System.out.println(livres.size() + " livres importés.");
            }

            // Importer les users
            List<Map<String, Object>> users = (List<Map<String, Object>>) data.get("users");
            if (users != null) {
                for (Map<String, Object> userData : users) {
                    User user = new User(
                            (Integer) userData.get("id"),
                            (String) userData.get("name"),
                            (String) userData.get("email")
                    );
                    userService.addUser(user.getNom(), user.getEmail());
                }
                System.out.println(users.size() + " utilisateurs importés.");
            }

            // Importer les emprunts
            List<Map<String, Object>> emprunts = (List<Map<String, Object>>) data.get("emprunts");
            if (emprunts != null) {
                for (Map<String, Object> empruntData : emprunts) {
                    try {
                        // Récupérer les informations de l'emprunt
                        int id = (Integer) empruntData.get("id");
                        int idLivre = (Integer) empruntData.get("id_livre"); // Utiliser "livreId" pour correspondre à votre JSON
                        int idUser = (Integer) empruntData.get("id_user");   // Utiliser "userId" pour correspondre à votre JSON

                        // Convertir les dates en LocalDate à partir des tableaux
                        List<Integer> dateEmpruntList = (List<Integer>) empruntData.get("date_emprunt");
                        LocalDate dateEmprunt = LocalDate.of(
                                dateEmpruntList.get(0), // Année
                                dateEmpruntList.get(1), // Mois
                                dateEmpruntList.get(2)  // Jour
                        );

                        List<Integer> dateRetourList = (List<Integer>) empruntData.get("date_retour");
                        LocalDate dateRetour = null;
                        if (dateRetourList != null && !dateRetourList.isEmpty()) {
                            dateRetour = LocalDate.of(
                                    dateRetourList.get(0), // Année
                                    dateRetourList.get(1), // Mois
                                    dateRetourList.get(2)  // Jour
                            );
                        }

                        // Créer un objet Emprunt
                        Emprunt emprunt = new Emprunt(id, idLivre, idUser, dateEmprunt, dateRetour);

                        // Ajouter l'emprunt au service
                        empruntService.addEmprunt(emprunt);
                    } catch (Exception e) {
                        System.out.println("Erreur lors de l'importation d'un emprunt : " + e.getMessage());
                    }
                }
                System.out.println(emprunts.size() + " emprunts importés.");
            }

            System.out.println("Importation réussie depuis le fichier JSON.");

        } catch (Exception e) {
            System.out.println("Erreur lors de l'importation : " + e.getMessage());
            e.printStackTrace();
        }
    }
}

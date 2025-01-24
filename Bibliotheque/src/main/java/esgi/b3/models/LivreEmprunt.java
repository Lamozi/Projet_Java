package esgi.b3.models;
import java.time.LocalDate;

public class LivreEmprunt {
    private final int id;
    private final String titre;
    private final LocalDate dateEmprunt;
    private final LocalDate dateRetour;

    public LivreEmprunt(int id, String titre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.titre = titre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return dateRetour;
    }
}

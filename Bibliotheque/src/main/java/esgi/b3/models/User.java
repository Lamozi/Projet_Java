package esgi.b3.models;

public class User {
    private int id;
    private String nom;
    private String email;

    /**
     * Constructeur
     * @param id    id de l'utilisateur
     * @param nom   nom de l'utilisateur
     * @param email email de l'utilisateur
     */
    public User(int id, String nom, String email) {
        this.id = id;
        this.nom = nom;
        this.email = email;
    }

    /** Getters, setters */
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}


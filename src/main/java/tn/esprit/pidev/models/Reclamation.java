package tn.esprit.pidev.models;
public class Reclamation {
    private int id;
    private String description;
    private String sujet;
    private int clientId;

    public Reclamation(int id) {
        this.id = id;
    }

    public Reclamation(String description, String sujet, int clientId) {
        this.description = description;
        this.sujet = sujet;
        this.clientId = clientId;
    }

    public Reclamation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", sujet='" + sujet + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}

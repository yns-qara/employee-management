package qara.younes.tprappeljava.bean;

import java.util.Date;
import java.util.List;

public class Employe {
    private int matricule;
    private String nom, prenom;

    private String specialite;
    private Date date_embauche;
    private char sexe;

    public Employe() {
    }

    public Employe(int matricule, String nom, String prenom, String specialite, Date date_embauche, char sexe) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.date_embauche = date_embauche;
        this.sexe = sexe;
    }

    public int getMatricule() {
        return matricule;
    }

    public void setMatricule(int matricule) {
        this.matricule = matricule;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Date getDate_embauche() {
        return date_embauche;
    }

    public void setDate_embauche(Date date_embauche) {
        this.date_embauche = date_embauche;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "matricule=" + matricule +
                '}';
    }

}

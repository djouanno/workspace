package fr.esir.xml.question6;

public class Personne
{
    private String nom;
    private int    age;
    private double taille;
    private Ville  ville;

    public Personne() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(final String nom) {
        this.nom = nom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public double getTaille() {
        return taille;
    }

    public void setTaille(final double taille) {
        this.taille = taille;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

}

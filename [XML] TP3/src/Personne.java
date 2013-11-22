import java.io.Serializable;

public class Personne implements Serializable
{
    private static final long serialVersionUID = -4234001588208860834L;

    private String            nom;
    private int               age;
    private double            taille;

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

}

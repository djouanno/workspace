package fr.esir.xml.question6;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

public class Question6
{

    public static void main(final String[] args) {
        // Creation de l’objet
        final Personne laPersonne = new Personne();
        final Ville ville = new Ville();

        laPersonne.setNom("Durant");
        laPersonne.setAge(28);
        laPersonne.setTaille(1.80);

        ville.setCodePostal("35000");
        ville.setNom("Rennes");

        laPersonne.setVille(ville);

        final Mapping mapping = new Mapping();
        try {
            mapping.loadMapping("map/mapping.xml");
            // Creation du writer
            final FileWriter writer = new FileWriter(new File("question6.xml"));
            // Creation d'un sérialisateur
            final Marshaller marshaller = new Marshaller(writer);
            marshaller.setMapping(mapping);
            // sérialisation
            marshaller.marshal(laPersonne);
        } catch (IOException | MappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final MarshalException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final ValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

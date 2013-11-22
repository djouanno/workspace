import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class GenererDOM4J
{

    private static final Logger LOGGER = Logger.getLogger(GenererDOM4J.class);

    /**
     * @param args
     */
    public static void main(final String[] args) {
        final Document document = DocumentHelper.createDocument();

        final Element personne = DocumentHelper.createElement("personne");
        final Element nom = DocumentHelper.createElement("nom");
        final Element age = DocumentHelper.createElement("age");
        final Element taille = DocumentHelper.createElement("taille");
        final Element adresse = DocumentHelper.createElement("adresse");
        final Element ville = DocumentHelper.createElement("ville");
        final Element voie = DocumentHelper.createElement("voie");

        nom.setText("Durant");
        age.setText("28");
        taille.setText("1.80");
        ville.addAttribute("codePostal", "35000");
        ville.setText("Rennes");
        voie.addAttribute("type", "rue");
        voie.setText("de l'Alma");

        adresse.add(ville);
        adresse.add(voie);

        personne.add(nom);
        personne.add(age);
        personne.add(taille);
        personne.add(adresse);

        document.add(personne);
        final OutputFormat format = OutputFormat.createPrettyPrint();
        XMLWriter writer;
        try {
            final File file = new File("personne.xml");
            writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(document);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}

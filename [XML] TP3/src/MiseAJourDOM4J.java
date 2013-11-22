import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class MiseAJourDOM4J
{

    public static void main(final String[] args) {

        final SAXReader reader = new SAXReader();
        try {
            final Document document = reader.read("personne.xml");

            final Node ville = document.selectSingleNode("//personne/adresse/ville");

            ville.setText("Vern sur seiche");

            final OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;
            final File file = new File("personneNew.xml");
            writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(document);

        } catch (final DocumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

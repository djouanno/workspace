import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Question4
{
    public static void main(final String[] args) {
        final SAXReader reader = new SAXReader();
        try {
            final Document document = reader.read("carnetDAdresse.xml");

            final List<Node> villes = document.selectNodes("//carnetDAdresse/carteDeVisite/adresse/ville[text()='Rennes']");

            for (final Node node : villes) {
                ((Element) node).setAttributeValue("codePostal", "35000");
            }

            final OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;
            final File file = new File("question4.xml");
            writer = new XMLWriter(new FileOutputStream(file), format);
            writer.write(document);

        } catch (final DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}

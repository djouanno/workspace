import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Question3
{

    public static void main(final String[] args) {
        final SAXReader reader = new SAXReader();
        try {
            final Document document = reader.read("carnetDAdresse.xml");

            final List<Node> villes = document.selectNodes("//carnetDAdresse/carteDeVisite/adresse/ville[text()='Vannes']");
            System.out.println(villes.size());

        } catch (final DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

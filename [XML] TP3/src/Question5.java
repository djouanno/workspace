import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

public class Question5
{

    public static void main(final String[] args) {

        final Personne personne = new Personne();
        personne.setNom("Durant");
        personne.setAge(28);
        personne.setTaille(1.80);

        try {
            final FileWriter file = new FileWriter(new File("question5.xml"));
            Marshaller.marshal(personne, file);
            file.close();
        } catch (MarshalException | ValidationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

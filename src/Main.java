import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, XMLStreamException {
        //HttpURLConnection myConnection;
        ////set the target xml file
        //String filePath = "src/data-sample/datasets-source-sample.xml";
        ////parse the file
        //RequestHandler myRequestHandler = new RequestHandler();
        //myConnection = myRequestHandler.readRequest();

        //Testing

        //create corresponding requests
        String baseUrl = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi";
        String filePath = "src/data-sample/datasets-source-sample.xml";
        RequestHandler myRequestHandler1 = new RequestHandler();
        RequestHandler myRequestHandler2 = new RequestHandler("src/data-sample/test.xml");

        System.out.println(myRequestHandler2.readRequest());
        System.out.println(myRequestHandler2.readRequest());
        System.out.println(myRequestHandler2.readRequest());
        System.out.println(myRequestHandler2.readRequest());
        System.out.println(myRequestHandler2.readRequest());

        //get response
    }
}
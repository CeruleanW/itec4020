import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Queue;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        //set the target xml file
        String filePath = "src/data-sample/datasets-source-sample.xml";
        //parse the file
        RequestHandler myRequestHandler = new RequestHandler();
        Queue<String> titles = null;
        try {
            titles = myRequestHandler.getTitles(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Testing

        //System.out.println(titles);
        //try {
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        //create corresponding requests
        String baseUrl = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi";
        String currentTitle = (titles != null) ? titles.poll() : null;
        HttpURLConnection myConnection;
        while (currentTitle != null) {
            try {
                //myConnection = myRequestHandler.createSingleRequest("John Snow and modern-day environmental epidemiology.", baseUrl);
                //    myRequestHandler.createSingleRequest("John Snow and modern-day environmental epidemiology.", "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi");
                myConnection = myRequestHandler.createSingleRequest(currentTitle, baseUrl);
                //send the request
                myRequestHandler.sendRequest(myConnection);

                currentTitle = titles.poll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //get response
    }
}
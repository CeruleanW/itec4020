import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.ws.Response;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;

public class RequestHandler {
    private SAXParser saxParser;
    private SAXContentHandler saxHandler;

    //    Constructor
    public RequestHandler() throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        this.saxParser = spf.newSAXParser();
        this.saxHandler = new SAXContentHandler();
    }

    public LinkedList<String> getTitles(String filePath) throws IOException, SAXException {
        File xmlFile = new File(filePath);
        this.saxParser.parse(xmlFile, saxHandler);
        LinkedList<String> titles = (LinkedList<String>) this.saxHandler.getArticleTitles();
        return titles;
    }

    private HttpURLConnection createRequest(List query, String requestUrl) throws IOException {
        HttpURLConnection connection = null;
//        // Create a neat value object to hold the URL
//        URL url = new URL(requestUrl);
//
//        // Open a connection(?) on the URL(??) and cast the response(???)
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//        // Now it's "open", we can set the request method, headers etc.
//        connection.setRequestProperty("accept", "application/json");
//
//        // This line makes the request
//        InputStream responseStream = connection.getInputStream();
//
//        // Manually converting the response body InputStream to APOD using Jackson
////        ObjectMapper mapper = new ObjectMapper();
////        APOD apod = mapper.readValue(responseStream, APOD.class);
//
        return connection;
    }

    //    send request and get the response XML data
    private Response getResponse(HttpURLConnection request) {
        return null;
    }
}
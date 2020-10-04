import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class RequestHandler {
    private SAXParser saxParser;
    private SAXContentHandler saxHandler;

    //Constructor
    public RequestHandler() throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        this.saxParser = spf.newSAXParser();
        //use our own ContentHandler
        this.saxHandler = new SAXContentHandler();
    }

    //  return a linked list of ArticleTitles from the XML document
    public LinkedList<String> getTitles(String filePath) throws IOException, SAXException {
        File xmlFile = new File(filePath);
        this.saxParser.parse(xmlFile, saxHandler);
        return (LinkedList<String>) this.saxHandler.getArticleTitles();
    }

    //  create a request by the query string and base url
    public HttpURLConnection createSingleRequest(String query, String baseUrl) throws IOException {
        HttpURLConnection connection;

        String encodeQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.name());
        String requestUrl = baseUrl + "?db=pubmed&term=" + encodeQuery + "&field=title";
        URL url = new URL(requestUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("accept", "application/xhtml+xml");

//        connection.setRequestProperty("Content-Type",
//                "application/xhtml+xml; charset=UTF-8");

        //setting the connect and read timeouts
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    //send request and get the response XML data
    //dont know how to handle the response yet 没想好
    public InputStream sendRequest(HttpURLConnection connection) throws IOException {
        // Fire the request
        InputStream responseStream = connection.getInputStream();
        return responseStream;
    }
}
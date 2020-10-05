import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RequestHandler {
    private boolean isTitle = false;
    private XMLEventReader eventReader;

    //Constructor - default file path: src/data-sample/datasets-source-sample.xml
    public RequestHandler() throws FileNotFoundException, XMLStreamException {
        String filePath = "src/data-sample/datasets-source-sample.xml";
        File xmlFile = new File(filePath);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        this.eventReader = factory.createXMLEventReader(new FileReader(xmlFile));
    }

    //Constructor - specified file path
    public RequestHandler(String filePath) throws FileNotFoundException, XMLStreamException {
        File xmlFile = new File(filePath);
        XMLInputFactory factory = XMLInputFactory.newInstance();
        this.eventReader = factory.createXMLEventReader(new FileReader(xmlFile));
    }

    //  create a request by the query string and base url
    public HttpURLConnection createSingleRequest(String requestUrl) throws IOException {
        HttpURLConnection connection;
        URL url = new URL(requestUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("accept", "application/xhtml+xml");
//      connection.setRequestProperty("Content-Type", "application/xhtml+xml; charset=UTF-8");

        //setting the connect and read timeouts
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        return connection;
    }

    //iterate one title from the local xml document, return a string request url contains the title
    public String readRequest() throws IOException, XMLStreamException {
        String baseUrl = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi";

        String title = this.getArticleTitle(eventReader);
        String requestUrl = this.createSingleUrlByTitle(title, baseUrl);
        return requestUrl;
    }

    public String createSingleUrlByTitle(String queryTitle, String baseUrl) throws UnsupportedEncodingException {
        String encodeQuery = URLEncoder.encode(queryTitle, StandardCharsets.UTF_8.name());
        String requestUrl = baseUrl + "?db=pubmed&term=" + encodeQuery + "&field=title";
        return requestUrl;
    }

    //return an article title as String, or return null if no more title
    public String getArticleTitle(XMLEventReader eventReader) throws XMLStreamException {
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement element = (StartElement) event;
                if (element.getName().toString().equalsIgnoreCase("ArticleTitle")) {
                    isTitle = true;
                }
            }

            if (event.isEndElement()) {
                EndElement element = (EndElement) event;
                if (element.getName().toString().equalsIgnoreCase("ArticleTitle")) {
                    isTitle = false;
                }
            }

            if (event.isCharacters()) {
                Characters element = (Characters) event;
                if (isTitle && !element.isWhiteSpace()) {
                    return element.getData();
                }
            }
        }
        return null;
    }
}
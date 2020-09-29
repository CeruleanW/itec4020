import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        //set the target xml file
        String filePath = "src/data-sample/datasets-source-sample.xml";
//        parse the file
        RequestHandler myRequestHandler = new RequestHandler();
        List<String> titles = null;
        try {
            titles = myRequestHandler.getTitles(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(titles);

//        send request
//        get response
    }
}
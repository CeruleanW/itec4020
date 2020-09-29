import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.LinkedList;
import java.util.List;

public class SAXContentHandler extends DefaultHandler {
    private final List<String> articleTitles = new LinkedList<String>();
    private boolean hasTitle = false;
    private String articleTitle = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equalsIgnoreCase("ArticleTitle")) {
            hasTitle = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equalsIgnoreCase("ArticleTitle")) {
            articleTitles.add(articleTitle);
            hasTitle = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (hasTitle) {
            articleTitle = new String(ch, start, length);
            hasTitle = false;
        }
    }

    public List<String> getArticleTitles() {
        return this.articleTitles;
    }
}

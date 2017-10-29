package ru.grigoryev.optimization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for building and parsing
 * XML files using DOM.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.10.2017
 */
public class DomXmlParser implements XmlBuilder {
    /**
     * Used for logging.
     */
    private final static Logger DOM_XML_LOGGER = LoggerFactory.getLogger(DomXmlParser.class);

    @Override
    public void buildXml(List<String> results, String fileName) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element rootElement = document.createElement("entries");
            Element entryElement;
            Element fieldElement;
            Text fieldText;
            document.appendChild(rootElement);

            try {
                for (String field : results) {
                    entryElement = document.createElement("entry");
                    fieldElement = document.createElement("field");
                    fieldText = document.createTextNode(field);
                    rootElement.appendChild(entryElement);
                    entryElement.appendChild(fieldElement);
                    fieldElement.appendChild(fieldText);
                }
            } catch (DOMException e) {
                DOM_XML_LOGGER.error(e.getMessage(), e);
            }
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(fileName)));
            } catch (FileNotFoundException | TransformerException e) {
                DOM_XML_LOGGER.error(e.getMessage(), e);
            }
        } catch (ParserConfigurationException e) {
            DOM_XML_LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Integer> parseXml(String filePath) {
        List<Integer> numbers = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(filePath);
            Document document = builder.parse(file);
            Element entries = document.getDocumentElement();
            NodeList entriesChildren = entries.getChildNodes();
            for (int i = 0; i < entriesChildren.getLength(); i++) {
                Node child = entriesChildren.item(i);
                if (child instanceof Element) {
                    Element entry = (Element) child;
                    numbers.add(Integer.parseInt(entry.getAttribute("field").trim()));
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            DOM_XML_LOGGER.error(e.getMessage(), e);
        }
        return numbers;
    }
}

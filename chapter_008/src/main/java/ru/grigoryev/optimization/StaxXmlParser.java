package ru.grigoryev.optimization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for building and parsing
 * XML files using StAX.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.10.2017
 */
public class StaxXmlParser implements XmlBuilder {
    /**
     * Used for logging.
     */
    private final static Logger STAX_XML_LOGGER = LoggerFactory.getLogger(StaxXmlParser.class);

    @Override
    public void buildXml(List<String> numbers, String fileName) {

        XMLStreamWriter writer = null;
        try {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            writer = factory.createXMLStreamWriter(new FileOutputStream("temporary.xml"), "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("entries");
            for (String numbrer : numbers) {
                writer.writeStartElement("entry");
                writer.writeStartElement("field");
                writer.writeCharacters(numbrer);
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new StreamSource(new BufferedInputStream(new FileInputStream("temporary.xml"))),
                    new StreamResult(new FileOutputStream(fileName)));
        } catch (XMLStreamException | FileNotFoundException e) {
            STAX_XML_LOGGER.error(e.getMessage(), e);
        } catch (TransformerException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (XMLStreamException e) {
                    STAX_XML_LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public List<Integer> parseXml(String filePath) {
        List<Integer> numbers = new ArrayList<>();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser =
                    factory.createXMLStreamReader(new FileReader(filePath));
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (parser.getLocalName().equals("entry")) {
                        String value = parser.getAttributeValue(null, "field");
                        if (value != null) {
                            numbers.add(Integer.parseInt(value));
                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            STAX_XML_LOGGER.error(e.getMessage(), e);
        }
        return numbers;
    }
}

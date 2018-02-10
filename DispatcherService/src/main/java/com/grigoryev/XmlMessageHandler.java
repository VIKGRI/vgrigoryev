package com.grigoryev;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class supplies the methods
 * for process xml files.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.02.2018
 */
public class XmlMessageHandler implements XmlHandler {
    /**
     * Used for logging.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(XmlMessageHandler.class);

    @Override
    public String buildXml(String fileName, int id) {
        XMLStreamWriter writer = null;
        fileName = String.format("%s.xml", fileName);
        try {
            XMLOutputFactory factory = XMLOutputFactory.newInstance();
            File file = new File("temporary.xml");
            if (!file.exists()) {
                file.createNewFile();
            }
            file.deleteOnExit();
            writer = factory.createXMLStreamWriter(new FileOutputStream(file), "UTF-8");

            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("message");
            writer.writeEmptyElement("target");
            writer.writeAttribute("id", String.valueOf(id));

            writer.writeStartElement("sometags");
            for (int i = 0; i < 3; i++) {
                writer.writeStartElement("data");
                writer.writeCharacters(" ");
                writer.writeEndElement();
            }
            writer.writeEndElement();

            writer.writeEndElement();
            writer.writeEndDocument();

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new StreamSource(new BufferedInputStream(new FileInputStream(file))),
                    new StreamResult(new FileOutputStream(fileName)));
        } catch (XMLStreamException | FileNotFoundException | TransformerException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (XMLStreamException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
        return fileName;
    }

    @Override
    public Integer parseXml(String filePath) {
        Integer id = null;
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader parser =
                    factory.createXMLStreamReader(new FileReader(filePath));
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (parser.getLocalName().equals("target")) {
                        String value = parser.getAttributeValue(null, "id");
                        if (value != null) {
                            id = Integer.valueOf(value);
                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return id;
    }

    @Override
    public synchronized void assignId(String filePath, int messageId) {
        try {
            File xmlFile = new File(filePath);
            SAXBuilder builder = new SAXBuilder();
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            Element dispatched = new Element("dispatched").setAttribute("id", String.format("%s", messageId));
            rootNode.addContent(0, dispatched);
            XMLOutputter xmlWriter = new XMLOutputter(Format.getPrettyFormat());
            String fileName = String.format("%1$s_disp_%2$s.xml", xmlFile, messageId);
            xmlWriter.output(document, new FileOutputStream(fileName));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (JDOMException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}

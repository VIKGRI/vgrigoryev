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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Provides methods for connecting to specified
 * database, populating it with values and
 * to use it in calculations.
 *
 * @author vgrigoryev
 * @version 1
 * @since 25.10.2017
 */
public class DBManager {
    /**
     * Database URL to connect.
     */
    private String url;
    /**
     * Number defines included upper bound of range from 1 to Number
     * to perform calculations with.
     */
    private int number;
    /**
     * Used for logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);

    /**
     * Sets database url to connect.
     * @param url specified url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets upper bound of range from 1 to Number.
     * @param number upper bound of range from 1 to Number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Connects to specified database and populates it with
     * numbers. Then reads values from database and returns it as a list.
     * @return list of numbers from database
     */
    public List<String> connectAndPopulateDB() {
        ArrayList<String> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url);) {
            //Create table if not exists
            try (PreparedStatement createStatment =
                         connection.prepareStatement(
                                 "create table if not exists my_tab (field int primary key not null)");) {
                createStatment.execute();
            }
            //Clear table if it contains any data
            try (PreparedStatement clearStatment =
                         connection.prepareStatement("delete from my_tab")) {
                clearStatment.executeUpdate();
            }
            //Inserts into table new data
            try (PreparedStatement insertStatment =
                         connection.prepareStatement("insert into my_tab (field) values (?)")) {
                for (int i = 1; i <= this.number; i++) {
                    insertStatment.setInt(1, i);
                    insertStatment.executeUpdate();
                }
            }

            //Extracts data from table
            try (Statement retrieveStatment =
                         connection.createStatement()) {
                ResultSet resultSet = retrieveStatment.executeQuery("SELECT * FROM my_tab");
                while (resultSet.next()) {
                   results.add(Integer.toString(resultSet.getInt("field")));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return results;
    }

    /**
     * Constructs XML document with specified name based on
     * information of list of results.
     * @param results list of results from database
     * @param fileName specified name for XML document
     */
    public void constructXml(List<String> results, String fileName) {
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
                LOG.error(e.getMessage(), e);
            }
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(new DOMSource(document), new StreamResult(new FileOutputStream(fileName)));
            } catch (FileNotFoundException | TransformerException e) {
                LOG.error(e.getMessage(), e);
            }
        } catch (ParserConfigurationException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Parses XML document and computes the sum
     * of integers stored in this document.
     * @param path Specified file path to parse and compute
     * @return sum of integers from XML document
     */
    public long parseAndCount(String path) {
        long sum = 0;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(path);
            Document document = builder.parse(file);
            Element entries = document.getDocumentElement();
            NodeList entriesChildren = entries.getChildNodes();
            for (int i = 0; i < entriesChildren.getLength(); i++) {
                Node child = entriesChildren.item(i);
                if (child instanceof Element) {
                    Element entry = (Element) child;
                    sum += Integer.parseInt(entry.getAttribute("field").trim());
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOG.error(e.getMessage(), e);
        }
        return sum;
    }

    /**
     * Transforms one XML document to another XML document
     * with a usage of XSLT.
     * @param fromFilePath Specified XML document to transform.
     * @param toFilePath Specified name for new XML document.
     * @param styleSheet Style sheet file which XSLT use.
     */
    public void transformXml(String fromFilePath, String toFilePath, String styleSheet) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer(new StreamSource(styleSheet));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transformer.transform(new StreamSource(fromFilePath), new StreamResult(toFilePath));
        } catch (TransformerException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

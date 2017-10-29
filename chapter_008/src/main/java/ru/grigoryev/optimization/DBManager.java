package ru.grigoryev.optimization;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.*;

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
     * Provides methods for parsing and building xml files.
     */
    private XmlBuilder xmlBuilder;
    /**
     * Used for logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);

    /**
     * Constructor.
     *
     * @param url        specified url
     * @param number     upper bound of range from 1 to Number
     * @param xmlBuilder xml builder which provides methods for parsing and building xml files
     */
    public DBManager(String url, int number, XmlBuilder xmlBuilder) {
        this.url = url;
        this.number = number;
        this.xmlBuilder = xmlBuilder;
    }

    /**
     * Connects to specified database and populates it with
     * numbers. Then reads values from database and returns it as a list.
     *
     * @return list of numbers from database
     */
    public List<String> connectAndPopulateDB() {
        ArrayList<String> results = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(this.url);) {
            LOG.info(Boolean.toString(connection.getMetaData().supportsBatchUpdates()));
            connection.setAutoCommit(false);
            //Create table if not exists and clear it if exists
            try (Statement statement = connection.createStatement()) {
                statement.addBatch("CREATE TABLE IF NOT EXISTS my_tab (field INT PRIMARY KEY NOT NULL)");
                statement.addBatch("DELETE FROM my_tab");
                statement.executeBatch();
            }
            connection.setAutoCommit(true);

            //Inserts into table new data
            connection.setAutoCommit(false);
            try (PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO my_tab (field) VALUES (?)")) {
                for (int i = 1; i <= this.number; i++) {
                    insertStatement.setInt(1, i);
                    insertStatement.addBatch();
                }
                insertStatement.executeBatch();
            }
            connection.setAutoCommit(true);

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
     *
     * @param results  list of results from database
     * @param fileName specified name for XML document
     */

    public void constructXml(List<String> results, String fileName) {
        this.xmlBuilder.buildXml(results, fileName);
    }

    /**
     * Parses XML document and computes the sum
     * of integers stored in this document.
     *
     * @param path Specified file path to parse and compute
     * @return sum of integers from XML document
     */
    public long parseAndCount(String path) {
        long sum = 0;
        List<Integer> numbers = this.xmlBuilder.parseXml(path);
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    /**
     * Transforms one XML document to another XML document
     * with a usage of XSLT.
     *
     * @param fromFilePath Specified XML document to transform.
     * @param toFilePath   Specified name for new XML document.
     * @param styleSheet   Style sheet file which XSLT use.
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

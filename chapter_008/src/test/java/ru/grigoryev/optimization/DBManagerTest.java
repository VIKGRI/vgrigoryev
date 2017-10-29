package ru.grigoryev.optimization;

import org.junit.Test;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing DBManager class.
 *
 * @author vgrigoryev
 * @version 1
 * @since 25.10.2017
 */
public class DBManagerTest {
    /**
     * Number to compute in the test.
     */
    private static final int NUMBER = 100;
    /**
     * Method for testing DBManager's methods.
     */
    @Test
    public void whenComputeIntervalFromOneToOneHundredThenResultIsEqualToExpectedValue() {
        // Using DOM for xml building and parsing
        //DBManager dbManager = new DBManager("jdbc:sqlite:test.db", NUMBER, new DomXmlParser());

        // Using StAX for XML building and parsing.
        DBManager dbManager = new DBManager("jdbc:sqlite:test.db", NUMBER, new StaxXmlParser());

        List<String> resultSet = dbManager.connectAndPopulateDB();
        dbManager.constructXml(resultSet, "1.xml");
        dbManager.transformXml("1.xml", "2.xml", "style.xsl");
        long result = dbManager.parseAndCount("2.xml");

        long expect = 0;
        for (int i = 1; i <= NUMBER; i++) {
            expect += i;
        }
        assertThat(result, is(expect));
    }
}
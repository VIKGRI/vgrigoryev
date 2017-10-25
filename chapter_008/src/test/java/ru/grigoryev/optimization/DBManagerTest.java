package ru.grigoryev.optimization;

import org.junit.Test;

import java.util.ArrayList;
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
    private static final int NUMBER = 1000;
    /**
     * Method for testing DBManager's methods.
     */
    @Test
    public void whenComputeIntervalFromOneToOneHundredThenResultIsEqualToExpectedValue() {
        DBManager dbManager = new DBManager();
        dbManager.setNumber(NUMBER);
        dbManager.setUrl("jdbc:sqlite:test.db");
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
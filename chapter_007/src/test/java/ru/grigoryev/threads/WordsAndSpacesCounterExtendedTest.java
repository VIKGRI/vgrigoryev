package ru.grigoryev.threads;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Class for WordsAndSpacesCounterExtended class testing.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class WordsAndSpacesCounterExtendedTest {
    /**
     * Method for testing count method.
     */
    @Test
    public void whenCountWordsAndSpacesThenAmountMatches() {

        WordsAndSpacesCounterExtended counter = new WordsAndSpacesCounterExtended();
        Pair<Integer, Integer> result = counter.count("text.txt");

        System.out.println(result.getFirst() + " " + result.getSecond());

        Pair<Integer, Integer> expect = new Pair<>(24, 27);

        assertThat(result, is(expect));
    }
}
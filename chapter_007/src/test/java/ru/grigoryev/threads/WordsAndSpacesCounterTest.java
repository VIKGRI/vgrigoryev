package ru.grigoryev.threads;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Class for WordsAndSpacesCounter class testing.
 *@author vgrigoryev
 *@since 05.10.2017
 *@version 1
 */
public class WordsAndSpacesCounterTest {
    /**
     * Method for testing count method.
     */
    @Test
    public void whenAddUserThenPassportMatches() {

        WordsAndSpacesCounter counter = new WordsAndSpacesCounter();
        Pair<Integer, Integer> result = counter.count("text.txt");

        System.out.println(result.getFirst() + " " + result.getSecond());

        Pair<Integer, Integer> expect = new Pair<>(32, 34);

        assertThat(result, is(expect));
    }

}
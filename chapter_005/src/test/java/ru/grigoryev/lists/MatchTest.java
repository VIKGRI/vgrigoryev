package ru.grigoryev.lists;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing Match's method.
 *
 * @author vgrigoryev
 * @since 14.10.2017
 */
public class MatchTest {

    /**
     * Testing containsAll() method.
     */
    @Test
    public void whenContainsThenTrue() {
        boolean doesContain = Match.containsAll("mama", "amam");

        assertThat(doesContain, is(true));
    }
    /**
     * Testing containsAll() method.
     */
    @Test
    public void whenDoesNotContainThenFalse() {
        boolean doesContain = Match.containsAll("mama", "amaa");

        assertThat(doesContain, is(false));
    }
}
package com.grigoryev.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing IOStreamService's methods.
 *
 * @author vgrigoryev
 * @since 13.12.2017
 */
public class IOStreamServiceTest {
    /**
     * isNumber() method testing.
     */
    @Test
    public void whenSeparateNumberExistsThenTrue() {
        String text = "There is number 33";

        IOStreamService service = new IOStreamService();
        boolean result = service.isNumber(new ByteArrayInputStream(text.getBytes()));

        assertThat(result, is(true));
    }

    /**
     * isNumber() method testing.
     */
    @Test
    public void whenSeparateDoesNotNumberExistThenFalse() {
        String text = "There is no number 9isNotANumber";

        IOStreamService service = new IOStreamService();
        boolean result = service.isNumber(new ByteArrayInputStream(text.getBytes()));

        assertThat(result, is(false));
    }

}
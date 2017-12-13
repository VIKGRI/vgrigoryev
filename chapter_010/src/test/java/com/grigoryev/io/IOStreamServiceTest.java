package com.grigoryev.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

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

    /**
     * dropAbuses() method testing.
     */
    @Test
    public void whenDeleteAbusesThenResultMatchesExpected() throws IOException {
        String result;
        try (FileOutputStream fos = new FileOutputStream("src\\resources\\MyFile.txt")) {

            String testText = "There is no number 9isNotANumber bad delete hello world";
            String[] abuses = {"bad", "no", "delete"};

            IOStreamService service = new IOStreamService();
            service.dropAbuses(new ByteArrayInputStream(testText.getBytes()), fos, abuses);

            StringBuilder res = new StringBuilder();
            String line;
            try (BufferedReader br = new BufferedReader(new FileReader(new File("src\\resources\\MyFile.txt")))) {
                while ((line = br.readLine()) != null) {
                    res.append(line);
                }
                result = res.toString().trim();
            }
        }

        assertThat(result, is("There is number 9isNotANumber hello world"));
    }

}
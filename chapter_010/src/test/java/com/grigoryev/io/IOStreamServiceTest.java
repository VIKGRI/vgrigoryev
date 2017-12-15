package com.grigoryev.io;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * naturalMergeSort() method testing.
     */
    @Test
    public void whenDoMergeSortByStringMatchThenFileIsSorted() throws IOException {
        File srcFile = new File("src\\resources\\result3.txt");
        File dstFile = new File("src\\resources\\output.txt");
        IOStreamService service = new IOStreamService();

        service.naturalMergeSort(srcFile, dstFile);
        boolean isOrderedByLength = true;
        try (RandomAccessFile src = new RandomAccessFile("src\\resources\\output.txt", "r")) {

            int previousElementLength = 0;
            String line;
            while ((line = src.readLine()) != null) {
                if (line.length() < previousElementLength) {
                    isOrderedByLength = false;
                    break;
                } else {
                    previousElementLength = line.length();
                }
            }
        }
        assertThat(isOrderedByLength, is(true));
    }
}
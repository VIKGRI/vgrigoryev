package com.grigoryev.io;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Class for testing IOStreamService's methods.
 *
 * @author vgrigoryev
 * @since 13.12.2017
 */
public class FileSorterTest {
    /**
     * sort() method testing.
     */
    @Test
    public void whenSortByStringLengthThenFileIsSorted() throws IOException {
        File srcFile = new File("src\\resources\\file.txt");
        File dstFile = new File("src\\resources\\output.txt");
        FileSorter service = new FileSorter();

        service.sort(srcFile, dstFile);
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
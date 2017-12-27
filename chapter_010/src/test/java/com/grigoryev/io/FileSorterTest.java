package com.grigoryev.io;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;

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
     * File separator.
     */
    private static final String FS = System.getProperty("file.separator");

    /**
     * sort() method testing.
     */
    @Test
    public void whenSortByStringLengthThenFileIsSorted() throws IOException {

        Path testDirPath = Paths.get(String.format("%s", System.getProperty("user.dir")));

        Path srcFile = testDirPath.resolve(String.format("src%sresources%<sfile.txt", FS));
        Path dstFile = testDirPath.resolve(String.format("src%sresources%<soutput.txt", FS));

        FileSorter service = new FileSorter();

        service.sort(srcFile.toFile(), dstFile.toFile());
        boolean isOrderedByLength = true;
        try (RandomAccessFile src = new RandomAccessFile(srcFile.toString(), "r")) {

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
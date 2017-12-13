package com.grigoryev.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Provides services for
 * input/output operations.
 *
 * @author vgrigoryev
 * @version 1
 * @since 13.12.2017
 */
public class IOStreamService {
    /**
     * Logger.
     */
    static private final Logger IO_SERVICE_LOGGER = LoggerFactory.getLogger(IOStreamService.class);

    /**
     * Detects whether there is a separate number
     * presents in the input stream or not.
     *
     * @param in specified input stream
     * @return true if number presents in the input stream, otherwise false
     */
    public boolean isNumber(InputStream in) {
        boolean doesContainNumber = false;
        if (in != null) {
            try (Scanner scanner = new Scanner(in)) {
                while (scanner.hasNext()) {
                    if (scanner.hasNextInt()) {
                        doesContainNumber = true;
                        break;
                    } else {
                        scanner.next();
                    }
                }
            } catch (Exception e) {
                IO_SERVICE_LOGGER.error(e.getMessage());
            }
        }
        return doesContainNumber;
    }

    /**
     * Deletes abuse words from the text.
     *
     * @param in specified input stream
     * @param out specified output stream
     * @param abuse specified abuse array
     */
    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) {
        if (in != null && out != null) {
            List<String> abuseWords = Arrays.asList(abuse);
            try (Scanner scanner = new Scanner(in);
                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out))) {
                String currentWord;
                while (scanner.hasNext()) {
                    currentWord = scanner.next();
                    if (!abuseWords.contains(currentWord)) {
                        bw.write(currentWord + " ");
                    }
                }
            } catch (IOException e) {
                IO_SERVICE_LOGGER.error(e.getMessage());
            }
        }
    }
}

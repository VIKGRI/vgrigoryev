package com.grigoryev.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

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
     * @param in    specified input stream
     * @param out   specified output stream
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

    /**
     * Gets data from the file, sorts it by string length
     * and outputs to the new file specified by the distance.
     * @param source specified source file
     * @param distance specified destination file
     */
    public void sort(File source, File distance) {
        try (RandomAccessFile src = new RandomAccessFile(source, "r");
             RandomAccessFile dst = new RandomAccessFile(distance, "rw")) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = src.readLine()) != null) {
                if (!line.trim().equals("")) {
                    lines.add(line.trim());
                }
            }
            Collections.sort(lines, Comparator.comparingInt(String::length));
            lines.stream().forEach((i)-> {
                try {
                    dst.write(String.format("%s\n", i).getBytes("UTF-8"));
                } catch (IOException e) {
                    IO_SERVICE_LOGGER.error(e.getMessage());
                }
            });
        } catch (FileNotFoundException e) {
            IO_SERVICE_LOGGER.error(e.getMessage());
        } catch (IOException e) {
            IO_SERVICE_LOGGER.error(e.getMessage());
        }
    }
}

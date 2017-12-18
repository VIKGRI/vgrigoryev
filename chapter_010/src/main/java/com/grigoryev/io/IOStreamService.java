package com.grigoryev.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
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
                        bw.write(currentWord);
                        if (scanner.hasNext()) {
                            bw.write(" ");
                        }
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
     *
     * @param source   specified source file
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
            lines.stream().forEach((i) -> {
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

    /**
     * Sorts strings in two files.
     * Implements external merge sort.
     *
     * @param source source file
     * @param distance destination file
     */
    public void naturalMergeSort(File source, File distance) {
        File helper1 = new File("src\\resources\\AuxiliaryOne.txt");
        File helper2 = new File("src\\resources\\AuxiliaryTwo.txt");

        try (RandomAccessFile src = new RandomAccessFile(source, "rw");
             RandomAccessFile dst = new RandomAccessFile(distance, "rw");
             RandomAccessFile auxiliaryOne = new RandomAccessFile(helper1, "rw");
             RandomAccessFile auxiliaryTwo = new RandomAccessFile(helper2, "rw")) {

            try (FileChannel srcChannel = src.getChannel(); FileChannel dstChannel = dst.getChannel();
                 FileChannel oAux = auxiliaryOne.getChannel(); FileChannel tAux = auxiliaryTwo.getChannel()) {

                dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
                String line;
                int previousLineLength = 0;
                boolean proceedSort = true;
                boolean captureSerie = true;

                while (proceedSort) {
                    proceedSort = false;
                    dst.seek(0);
                    while ((line = dst.readLine()) != null) {
                        String currentLine = line.trim();
                        if (captureSerie) {
                            if (previousLineLength <= currentLine.length()) {
                                auxiliaryOne.write(String.format("%s\n", currentLine).getBytes("UTF-8"));
                                previousLineLength = currentLine.length();
                            } else {
                                captureSerie = false;
                                previousLineLength = 0;
                            }
                        }
                        if (!captureSerie) {
                            proceedSort = true;
                            if (previousLineLength <= currentLine.length()) {
                                auxiliaryTwo.write(String.format("%s\n", currentLine).getBytes("UTF-8"));
                                previousLineLength = currentLine.length();
                            } else {
                                captureSerie = true;
                                auxiliaryOne.write(String.format("%s\n", currentLine).getBytes("UTF-8"));
                                previousLineLength = currentLine.length();
                            }
                        }
                    }
                    if (proceedSort) {
                        dstChannel.truncate(0);
                        merge(dst, auxiliaryOne, auxiliaryTwo);
                        oAux.truncate(0);
                        tAux.truncate(0);
                        captureSerie = true;
                        previousLineLength = 0;
                    }
                }
                merge(dst, auxiliaryOne, auxiliaryTwo);
                helper1.deleteOnExit();
                helper2.deleteOnExit();
            }
        } catch (FileNotFoundException e) {
            IO_SERVICE_LOGGER.error(e.getMessage());
        } catch (IOException e) {
            IO_SERVICE_LOGGER.error(e.getMessage());
        }
    }

    /**
     * Auxiliary function that merges strings
     * from two files.
     *
     * @param source RandomAccessFile connected with source file
     * @param auxiliaryFirst RandomAccessFile connected with first helper file
     * @param auxiliarySecond RandomAccessFile connected with second helper file
     * @throws IOException thrown if IO problems occur
     */
    private void merge(RandomAccessFile source, RandomAccessFile auxiliaryFirst, RandomAccessFile auxiliarySecond)
            throws IOException {
        auxiliaryFirst.seek(0);
        auxiliarySecond.seek(0);
        source.seek(0);
        String firstBuffer = auxiliaryFirst.readLine();
        String secondBuffer = auxiliarySecond.readLine();
        RandomAccessFile hasMoreStrings;

        while (firstBuffer != null && secondBuffer != null) {
            if (firstBuffer.length() <= secondBuffer.length()) {
                source.write(String.format("%s\n", firstBuffer).getBytes("UTF-8"));
                firstBuffer = auxiliaryFirst.readLine();
            } else {
                source.write(String.format("%s\n", secondBuffer).getBytes("UTF-8"));
                secondBuffer = auxiliarySecond.readLine();
            }
        }

        String remainBuffer;
        if (firstBuffer == null) {
            hasMoreStrings = auxiliarySecond;
            remainBuffer = secondBuffer;
        } else {
            hasMoreStrings = auxiliaryFirst;
            remainBuffer = firstBuffer;
        }

        if (firstBuffer != null || secondBuffer != null) {
            while (remainBuffer != null) {
                source.write(String.format("%s\n", remainBuffer).getBytes("UTF-8"));
                remainBuffer = hasMoreStrings.readLine();
            }
        }
    }
}

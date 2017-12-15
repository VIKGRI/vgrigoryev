package com.grigoryev.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileSorter {
    /**
     * Logger.
     */
    static private final Logger SERVICE_LOGGER = LoggerFactory.getLogger(IOStreamService.class);

    /**
     * Sorts strings in two files.
     * Implements external merge sort.
     *
     * @param source   source file
     * @param distance destination file
     */
    public void sort(File source, File distance) {
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
        } catch (IOException e) {
            SERVICE_LOGGER.error(e.getMessage());
        }
    }

    /**
     * Auxiliary function that merges strings
     * from two files.
     *
     * @param source          RandomAccessFile connected with source file
     * @param auxiliaryFirst  RandomAccessFile connected with first helper file
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

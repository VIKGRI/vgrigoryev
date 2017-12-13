package com.grigoryev.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
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
}

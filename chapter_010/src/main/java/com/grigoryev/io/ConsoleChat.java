package com.grigoryev.io;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * Class represents console chat.
 * User enters word or phrase and
 * chat returns random string from specified file.
 * Conversation is logged.
 *
 * @author vgrigoryev
 * @since 20.12.2017
 */
public class ConsoleChat {
    /**
     * Logger.
     */
    public static final Logger LOGGER = Logger.getLogger(ConsoleChat.class);
    /**
     * Text source file which is used by chat to choose random string.
     */
    private String sourceFile;

    /**
     * Constructor.
     * @param sourceFile text source file which is used by chat to choose random string
     */
    public ConsoleChat(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Launches console chat.
     */
    public void launch() {

        DOMConfigurator.configure("src/log4j.xml");
        try (Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
             RandomAccessFile reader = new RandomAccessFile(this.sourceFile, "r")) {
            long byteCount = reader.length();

            System.out.println(byteCount);

            String input = "";
            boolean isOnPause = false;
            while (!input.equals("закончить")) {
                System.out.println("Введите фразу: ");
                if (scanner.hasNext()) {
                    input = scanner.next().toLowerCase();
                    if (!isOnPause && input.equals("стоп")) {
                        isOnPause = true;
                        LOGGER.info("КОМАНДА СТОП: Программа приостановлена");
                    } else if (isOnPause && input.equals("продолжить")) {
                        isOnPause = false;
                        LOGGER.info("КОМАНДА ПРОДОЛЖИТЬ: Программа активирована");
                    } else if (input.equals("закончить")) {
                        LOGGER.info("КОМАНДА ЗАКОНЧИТЬ: Выход из программы");
                    } else if (!isOnPause) {
                        LOGGER.info(String.format("Пользователь: %s", input));
                        LOGGER.info(String.format("Чат: %s", this.randomStringFromFile(reader, byteCount)));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Auxiliary to select random string.
     * @param reader specified random access file stream
     * @param byteCount number of bytes in the file
     * @return chosen string
     * @throws IOException exception
     */
    private String randomStringFromFile(RandomAccessFile reader, long byteCount) throws IOException {
        long random = (long) (Math.random() * byteCount);
        reader.seek(random);
        if (reader.readChar() != '\n') {
            char ch;
            do {
                random -= 2;
                reader.seek(random);
                ch = (char) reader.readByte();
            } while (ch != '\n' && random >= 2);
        }
        return reader.readLine();
    }

    /**
     * Main.
     * @param args arguments
     */
    public static void main(String[] args) {
        ConsoleChat chat = new ConsoleChat("src\\resources\\result3.txt");
        chat.launch();
    }
}

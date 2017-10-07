package ru.grigoryev.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.Thread.currentThread;

/**
 *Class for counting words and spaces.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class WordsAndSpacesCounterExtended {
    /**
     * Number of words.
     */
    private int words = 0;
    /**
     * Number of spaces.
     */
    private int spaces = 0;
    /**
     * Limit time for program execution.
     */
    private static final long LIMIT_TIME = 1000;
    /**
     * Flag to figure out when interruption occurs.
     */
    private volatile boolean isTimeUp = false;

    /**
     * @param textPath text to process.
     * @return Pair with number of words and spaces in the specified text.
     */
    public Pair<Integer, Integer> count(String textPath) {
        ArrayList<String> text = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(textPath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                text.add(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Thread wordsCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String str : text) {
                    try {
                        boolean blank = true;
                        for (int i = 0; i < str.length(); i++) {
                            if (str.charAt(i) != ' ' && blank) {
                                words++;
                                blank = false;
                            }
                            if (str.charAt(i) == ' ' && !blank) {
                                blank = true;
                            }
                        }
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.out.println(currentThread().getName() + " was interrupted");
                        break;
                    }
                    if (isTimeUp) {
                        break;
                    } else {
                        System.out.println(currentThread().getName() + ": " + words);
                    }
                }
            }
        }, "wordsCounter");
        Thread spacesCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String str : text) {
                    try {
                        for (int i = 0; i < str.length(); i++) {
                            if (str.charAt(i) == ' ') {
                                spaces++;
                            }
                        }
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        System.out.println(currentThread().getName() + " was interrupted");
                        isTimeUp = true;
                    }
                    if (isTimeUp) {
                        break;
                    } else {
                        System.out.println(currentThread().getName() + ": " + spaces);
                    }
                }
            }
        }, "spacesCounter");
        System.out.println("Program started");
        long startTime = System.currentTimeMillis();
        wordsCounter.start();
        spacesCounter.start();

        while (wordsCounter.isAlive() || spacesCounter.isAlive()) {
            if (((System.currentTimeMillis() - startTime) > LIMIT_TIME)
                    && (wordsCounter.isAlive() || spacesCounter.isAlive())) {
                System.out.println("Time is up!");
                if (wordsCounter.isAlive()) {
                    wordsCounter.interrupt();
                }
                if (spacesCounter.isAlive()) {
                    spacesCounter.interrupt();
                }
            }
        }
        try {
            wordsCounter.join();
            spacesCounter.join();
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }
        System.out.println("Program is finished");
        return new Pair<>(words, spaces);
    }
}

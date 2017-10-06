package ru.grigoryev.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.Thread.currentThread;

/**
 *Class for counting words and spaces.
 *@author vgrigoryev
 *@since 05.10.2017
 *@version 1
 */
public class WordsAndSpacesCounter {
    /**
     * Number of words.
     */
    private int words = 0;
    /**
     * Number of spaces.
     */
    private int spaces = 0;

    /**
     *
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
                    System.out.println(currentThread().getName() + ": " + words);
                }
            }
        }, "wordsCounter");
        Thread spacesCounter = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String str : text) {
                    for (int i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == ' ') {
                            spaces++;
                        }
                    }
                    System.out.println(currentThread().getName() + ": " + spaces);
                }
            }
        }, "spacesCounter");

        wordsCounter.start();
        spacesCounter.start();

        try {
            wordsCounter.join();
            spacesCounter.join();
        } catch (InterruptedException iex) {
            iex.printStackTrace();
        }

        return new Pair<>(words, spaces);
    }
}

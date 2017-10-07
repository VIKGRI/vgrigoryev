package ru.grigoryev.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.Thread.currentThread;

/**
 *Class for counting characters in the text.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class CountChar implements Runnable {
    /**
     * Holds number of counted characters.
     */
    private int counter;
    /**
     * Text to count the amount of characters in.
     */
    private ArrayList<String> text;

    /**
     * Counter's getter.
     * @return number of characters in the text
     */
    public synchronized int getCounter() {
        return this.counter;
    }

    /**
     * Constructor.
     * @param textPath specified text
     */
    public CountChar(String textPath) {
        this.counter = 0;
        this.text = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(textPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                this.text.add(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        for (String str : text) {
            for (int i = 0; i < str.length(); i++) {
                if (Thread.interrupted()) {
                    System.out.println(Thread.currentThread().getName() + ": program was interrupted!");
                    return;
                }
                if (str.charAt(i) != ' ') {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        System.out.println(Thread.currentThread().getName() + ": program was interrupted!");
                        return;
                    }
                    this.counter++;
                    System.out.println(currentThread().getName() + ": " + this.counter);
                }
            }
        }
    }
}

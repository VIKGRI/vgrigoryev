package ru.grigoryev.threads;
/**
 *Class for counting characters in the text in specified time period.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class TimerCharCounter {
    /**
     * Counts characters in the specified text during no longer than specifird time.
     * @param textPath specified text
     * @param limitTime specifird time
     * @return number of characters
     */
    public int count(String textPath, long limitTime) {
        CountChar counter = new CountChar(textPath);
        Thread countChar = new Thread(counter, "countChar");
        Thread timer = new Thread(new Time(limitTime)); try {
            timer.join();
            countChar.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        timer.start();
        countChar.start();
        while (countChar.isAlive()) {
            if (!timer.isAlive()) {
                countChar.interrupt();
            }
        }
        if (!countChar.isAlive() && timer.isAlive()) {
            timer.interrupt();
        }

        System.out.println("metod: " + counter.getCounter());
        return counter.getCounter();
    }
}

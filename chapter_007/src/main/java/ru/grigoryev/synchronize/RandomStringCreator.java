package ru.grigoryev.synchronize;

import java.util.Random;

/**
 *Class provides random string building.
 *@author vgrigoryev
 *@since 10.10.2017
 *@version 1
 */
public class RanomStringCreator {
    /**
     *The characters to build the string.
     */
    private static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    /**
     *The length of the generated string.
     */
    private static final int LENGTH = 9;
    /**
     *Random object supplies random numbers in needed range.
     */
    private static Random random = new Random();
    /**
     *Method is used for creating one random string.
     *@return random string
     */
    public static String createRandomString() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int number = random.nextInt(ALPHA.length());
            char ch = ALPHA.charAt(number);
            builder.append(ch);
        }
        return builder.toString();
    }
}

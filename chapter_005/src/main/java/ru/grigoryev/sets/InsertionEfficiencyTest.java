package ru.grigoryev.sets;

import java.util.Random;

/**
 *Class provides measuring time of inserting and deleting in different collections.
 *@author vgrigoryev
 *@since 14.09.2017
 *@version 1
 * @param <E> Type of element stored in container
 */
public class InsertionEfficiencyTest<E> {
    /**
     *Main method.
     *@param args args
     */
    public static void main(String[] args) {
        InsertionEfficiencyTest test = new InsertionEfficiencyTest();

        SimpleSet<String> simpleArraySet = new SimpleSet<>();
        LinkedSimpleSet<String> simpleLinkedSet1 = new LinkedSimpleSet<>();
        LinkedSimpleSet<String> simpleLinkedSet2 = new LinkedSimpleSet<>();

        int numberOfStringsToAdd = 50000;
        String[] randomStrings = new String[numberOfStringsToAdd];
        for (int i = 0; i < numberOfStringsToAdd; i++) {
            randomStrings[i] = RanomStringArrayCreator.createRandomString();
        }

        long start = System.currentTimeMillis();
        for (String str : randomStrings) {
            simpleLinkedSet1.add(str);
        }
        long finish = System.currentTimeMillis();
        long time1 = finish - start;

        start = System.currentTimeMillis();
        for (String str : randomStrings) {
            simpleArraySet.add(str);
        }
        finish = System.currentTimeMillis();
        long time2 = finish - start;

        System.out.println("\n");
        System.out.println("Adding strings in collections: \n");
        System.out.println("Number of strings added: " + numberOfStringsToAdd + "\n");
        System.out.println("Time for SimpleLinkedSet add: " + time1);
        System.out.println("Time for SimpleArraySet add2: " + time2);
    }
}
/**
 *Class provides random string building.
 *@author vgrigoryev
 *@since 14.09.2017
 *@version 1
 */
class RanomStringArrayCreator {
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

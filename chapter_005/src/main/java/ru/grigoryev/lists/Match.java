package ru.grigoryev.lists;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class provides checking whether string contains symbols in the another string.
 * @author grigoryeev
 * @since 14.10.2017
 * @version 1
 */
public class Match {
    /**
     * Cheks whether string contains
     * symbols in the another string.
     * The symbols are not considered as unique.
     * @param left String where to find out matches.
     * @param right String to check.
     * @return true if left string contains all symbols from the right string.
     */
    public static boolean containsAll(String left, String right) {
        if (left.length() < right.length()) {
            return false;
        }
        char[] a = left.toCharArray();
        char[] b = right.toCharArray();

        Map<Character, Integer> leftStr = new LinkedHashMap<>();
        for (Character key: a) {
            if (leftStr.containsKey(key)) {
                leftStr.put(key, leftStr.get(key) + 1);
            } else {
                leftStr.put(key, 1);
            }
        }
        for (Character key: b) {
                leftStr.computeIfPresent(key, (k, v) -> v - 1);
                if (leftStr.containsKey(key) && leftStr.get(key) < 0) {
                    return false;
                }
        }
        return true;
    }
}

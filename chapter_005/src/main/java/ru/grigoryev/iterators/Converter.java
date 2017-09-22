package ru.grigoryev.iterators;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Converter of iterators.
 * @author vgrigoryev
 * @since 22.09.2017
 * @version 1
 */
public class Converter {
    /**
     * Each element in represents the movement through the Iterator<Iterator<Integer>>.
     * One element for one call of convert method.
     */
    private ArrayList<Iterator<Integer>> convertCalls = new ArrayList<>();
    /**
     * Represents position in the list.
     */
    private int position = 0;
    /**
     * Indicates whether Iterator<Iterator<Integer>> it has next Iterator<Integer>
     *     or at the beginning of convert method.
     */
    private int index = -1;

    /**
     * Convert Iterator<Iterator<Integer>> into Iterator<Integer>.
     * @param it Iterator<Iterator<Integer>> to convert
     * @return Iterator<Integer>
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        if (it.hasNext()) {
            convertCalls.add(it.next());
            index = position++;
        }

        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                boolean hasNext = false;
                if (index != -1) {
                    hasNext = convertCalls.get(index).hasNext();
                }
                return hasNext;
            }

            @Override
            public Integer next() {
                Integer result = null;
                if (index != -1) {
                    if (!convertCalls.get(index).hasNext() && it.hasNext()) {
                        convertCalls.set(index, it.next());
                    }
                    result = convertCalls.get(index).next();
                    if (!convertCalls.get(index).hasNext() && it.hasNext()) {
                        convertCalls.set(index, it.next());
                    }
                }
                return result;
            }
        };
    }
}

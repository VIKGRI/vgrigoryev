package ru.job4j.task;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class which has methods to orginize department's
 * hierarchy and manipulate that data.
 *
 * @author vgrigoryev
 * @version 1
 * @since 24.09.2017
 */
public class DepartmentSort2 {
    /**
     * Sorts an array in a specified by comparator order.
     * @param departments array of departments to sort
     * @param com specified comparator
     * @return set of departments sorted in specified order
     */
    private Set<String> sort(final String[] departments, Comparator<String> com) {
        Set<String> result = new TreeSet<>(com);
        int level = 1;
        String[] row;
        for (int i = 0; i < departments.length; i++) {
            row = departments[i].split("[\\W&&[^0-9]]");
            if (row.length == level) {
                result.add(departments[i]);
            } else if (row.length - level == 1 && level != 1) {
                level++;
                result.add(departments[i]);
            } else if (row.length - level > 1 || level == 1) {
                for (; level <= row.length; level++) {

                    String dep = row[0];

                    for (int k = 1; k < level; k++) {
                        dep += "\\" + row[k];
                    }
                    result.add(dep);
                }
            } else {
                i--;
                level = 1;
            }
        }
        return result;
    }

    /**
     * Sorts an array in descending order.
     * @param departments array of departments to sort
     * @return Sorted array of departments
     */
    public String[] descendingOrder(final String[] departments) {
        Comparator<String> com = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] s1 = o1.split("[\\W&&[^0-9]]");
                String[] s2 = o2.split("[\\W&&[^0-9]]");
                int comp;
                if (s1[0].equals(s2[0])) {
                    if (o1.length() == o2.length()) {
                        comp = o2.compareTo(o1);
                    } else {
                        comp = ((Integer) o1.length()).compareTo(o2.length());
                    }
                } else {
                    comp = o2.compareTo(o1);
                }
                return comp;
            }
        };
        Set<String> set = sort(departments, com);
        String[] result = new String[set.size()];
        set.toArray(result);
        return result;
    }
    /**
     * Sorts an array in ascending order.
     * @param departments array of departments to sort
     * @return Sorted array of departments
     */
    public String[] ascendingOrder(final String[] departments) {
        Comparator<String> com = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
        };
        Set<String> set = sort(departments, com);
        String[] result = new String[set.size()];
        set.toArray(result);
        return result;
    }
}

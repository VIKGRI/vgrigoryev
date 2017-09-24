package ru.job4j.task;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Class for DepartmentSortTest class testing.
 *@author vgrigoryev
 *@since 23.09.2017
 *@version 1
 */
public class DepartmentSortTest {
    /**
     * Method for testing ascendingOrder method.
     */
    @Test
    public void whenFormAndPrintDepartmentsAsccendingThenMatchesPattern() {
        String[] str = new String[]{
                "K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2",
                "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"
        };


        DepartmentSort2 ob = new DepartmentSort2();
        String[] result = ob.ascendingOrder(str);

        String[] expect = {"K1", "K1\\SK1", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K1\\SK2",
                "K2", "K2\\SK1", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};

        assertThat(result, is(expect));
    }

    /**
     * Method for testing descendingOrder method.
     */
    @Test
    public void whenFormAndPrintDepartmentsDesccendingThenMatchesPattern() {
        String[] str = new String[]{
                "K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2",
                "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"
        };


        DepartmentSort2 ob = new DepartmentSort2();
        String[] result = ob.descendingOrder(str);

        String[] expect = {"K2", "K2\\SK1", "K2\\SK1\\SSK2", "K2\\SK1\\SSK1", "K1",
                "K1\\SK2", "K1\\SK1", "K1\\SK1\\SSK2", "K1\\SK1\\SSK1"};

        assertThat(result, is(expect));
    }
}
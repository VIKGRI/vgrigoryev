package ru.job4j.task;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
     * Method for testing sortAsc method.
     */
    @Test
    public void whenFormAndPrintDepartmentsAsccendingThenMatchesPattern() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String[] str = new String[]{
                "K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2",
                "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"
        };
        DepartmentSort ob = new DepartmentSort();
        Node brances = ob.buildTree(str);
        ob.sortAsc(brances);
        ob.print(brances);

        assertThat(out.toString(), is(String.format(
                "K1" + "%s"
                        + "K1\\SK1" + "%<s"
                        + "K1\\SK1\\SSK1" + "%<s"
                        + "K1\\SK1\\SSK2" + "%<s"
                        + "K1\\SK2"  + "%<s"
                        + "K2" + "%<s"
                        + "K2\\SK1" + "%<s"
                        + "K2\\SK1\\SSK1" + "%<s"
                        + "K2\\SK1\\SSK2" + "%<s",
                System.getProperty("line.separator"))));
    }

    /**
     * Method for testing sortDesc method.
     */
    @Test
    public void whenFormAndPrintDepartmentsDesccendingThenMatchesPattern() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        String[] str = new String[]{
                "K1\\SK1", "K1\\SK2", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2",
                "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"
        };
        DepartmentSort ob = new DepartmentSort();
        Node brances = ob.buildTree(str);
        ob.sortDesc(brances);
        ob.print(brances);
        assertThat(out.toString(), is(String.format(
                "K2" + "%s"
                + "K2\\SK1" + "%<s"
                        + "K2\\SK1\\SSK2" + "%<s"
                        + "K2\\SK1\\SSK1" + "%<s"
                        + "K1" + "%<s"
                        + "K1\\SK2" + "%<s"
                        + "K1\\SK1" + "%<s"
                        + "K1\\SK1\\SSK2" + "%<s"
                        + "K1\\SK1\\SSK1" + "%<s",
                System.getProperty("line.separator"))));
    }
}
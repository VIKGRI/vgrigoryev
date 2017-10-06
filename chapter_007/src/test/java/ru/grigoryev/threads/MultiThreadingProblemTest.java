package ru.grigoryev.threads;

import org.junit.Test;

//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertThat;

/**
 *Class for demonstrating problems of concurrent programming.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class MultiThreadingProblemTest {
    /**
     * Method for testing demostrate method.
     */
    @Test
    public void whenIncrementsFromTwoThreadsThenUpdatesLost() {

        MultiThreadingProblem task = new MultiThreadingProblem();
        Value value = new Value(0);

        task.demonstrate(value);

        int result = value.getValue();
        System.out.println(result);
        boolean answer = result == 100;

        //assertThat(answer, is(false));
    }
}
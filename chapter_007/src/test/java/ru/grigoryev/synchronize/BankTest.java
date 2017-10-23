package ru.grigoryev.synchronize;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing Bank class.
 *
 * @author vgrigoryev
 * @version 1
 * @since 23.10.2017
 */
public class BankTest {
    /**
     * Method for testing getMaximumTrafficInterval method.
     */
    @Test
    public void whenComputeIntervalThenResultIsEqualToExpectedValue() {

        List<Visitor> visitors  = new ArrayList<>();
        visitors.add(new Visitor(10, 30));
        visitors.add(new Visitor(15, 45));
        visitors.add(new Visitor(60, 180));
        visitors.add(new Visitor(65, 90));
        visitors.add(new Visitor(18, 33));
        visitors.add(new Visitor(70, 95));
        visitors.add(new Visitor(20, 55));
        visitors.add(new Visitor(181, 185));
        visitors.add(new Visitor(85, 100));
        visitors.add(new Visitor(89, 100));
        visitors.add(new Visitor(40, 61));
        visitors.add(new Visitor(56, 59));
        Bank bank = new Bank(visitors);

        TrafficInterval result  = bank.getMaximumTrafficInterval();
        TrafficInterval expect  = new TrafficInterval();
        expect.setStart(89);
        expect.setEnd(90);
        expect.setVisitiorCount(5);

        assertThat(result, is(expect));
    }
}

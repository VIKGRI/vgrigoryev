package ru.grigoryev.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class represents bank.
 *
 * @author vgrigoryev
 * @version 1
 * @since 23.10.2017
 */
public class Bank {
    /**
     * Bank's visitors.
     */
    private List<Visitor> visitors;
    /**
     * Constructor.
     *
     * @param visitors bank's visitors.
     */
    public Bank(List<Visitor> visitors) {
        this.visitors = visitors;
    }

    /**
     * Gets copy of visitor's list.
     *
     * @return visitor's list.
     */
    public List<Visitor> getVisitors() {
        return new ArrayList<>(this.visitors);
    }

    /**
     * Gets the maximum visitors interval in the bank.
     *
     * @return interval with maximum visitors
     */
    public TrafficInterval getMaximumTrafficInterval() {
        TrafficInterval max = new TrafficInterval();
        List<TrafficInterval> intervals = new ArrayList<>();
        int index = 0;

        for (Visitor visitor : this.visitors) {
            TrafficInterval interval = new TrafficInterval(this.visitors, index);
            TrafficInterval result = interval.computeTrafficIntervals();
            intervals.add(result);
            index++;
        }

        for (TrafficInterval interval : intervals) {
            if (max.getVisitiorCount() < interval.getVisitiorCount()) {
                max = interval;
            }
        }
        return max;
    }
}

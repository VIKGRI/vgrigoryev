package ru.grigoryev.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *Class represents bank.
 *@author vgrigoryev
 *@since 23.10.2017
 *@version 1
 */
public class Bank {
    /**
     * Bank's visitors.
     */
    private List<Visitor> visitors;
    /**
     * Thread pool to compute max traffic according to each visitor in the bank.
     */
    private ExecutorService pool;
    /**
     * Constructor.
     * @param visitors bank's visitors.
     */
    public Bank(List<Visitor> visitors) {
        this.visitors = visitors;
        this.pool = Executors.newCachedThreadPool();
    }
    /**
     * Gets copy of visitor's list.
     * @return visitor's list.
     */
    public List<Visitor> getVisitors() {
        return new ArrayList<>(this.visitors);
    }
    /**
     * Gets the maximum visitors interval in the bank.
     * @return interval with maximum visitors
     */
    public TrafficInterval getMaximumTrafficInterval() {
        TrafficInterval max = new TrafficInterval();
        try {
            List<Future<TrafficInterval>> intervals = new ArrayList<>();
            int index = 0;
            for (Visitor visitor : this.visitors) {
                TrafficInterval interval = new TrafficInterval(this.visitors, index);
                Future<TrafficInterval> result = pool.submit(interval);
                intervals.add(result);
                index++;
            }
            try {
                for (Future<TrafficInterval> interval : intervals) {
                    TrafficInterval current = interval.get();
                    if (max.getVisitiorCount() < current.getVisitiorCount()) {
                        max = current;
                    }
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return max;
    }
}

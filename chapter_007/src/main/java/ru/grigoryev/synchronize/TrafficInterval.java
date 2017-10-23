package ru.grigoryev.synchronize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *Class represents information about amount
 * visitors within spicific time interval.
 *@author vgrigoryev
 *@since 23.10.2017
 *@version 1
 */
public class TrafficInterval implements Callable<TrafficInterval> {
    /**
     * Interval's start.
     */
    private long start;
    /**
     * Interval's end.
     */
    private long end;
    /**
     * Number of visitors.
     */
    private int visitiorCount;
    /**
     * List of visitors.
     */
    private List<Visitor> visitors;
    /**
     * Index defines visitor from whom to start
     * compute interval with maximum traffic.
     */
    private int index;

    /**
     * Constructor.
     */
    public TrafficInterval() {
        this.start = 0;
        this.end = 0;
        this.visitiorCount = 0;
        this.index = 0;
        this.visitors = new ArrayList<>();
    }

    /**
     * Constructor.
     * @param visitiors List of visitors
     * @param index index defines visitor from whom
     *              to start compute interval with maximum traffic
     */
    public TrafficInterval(List<Visitor> visitiors, int index) {
        this.start = visitiors.get(index).getComing();
        this.end = visitiors.get(index).getLeaving();
        this.visitiorCount = 1;
        this.index = index;
        this.visitors = visitiors;
    }

    /**
     * Sets interval's start.
     * @param start Interval's start
     */
    public void setStart(long start) {
        if (start >= 0) {
            this.start = start;
        }
    }

    /**
     * Sets interval's end.
     * @param end Interval's end
     */
    public void setEnd(long end) {
        if (end >= this.start) {
            this.end = end;
        }
    }

    /**
     * Gets interval's start.
     * @return Interval's start
     */
    public long getStart() {
        return this.start;
    }

    /**
     * Gets interval's end.
     * @return Interval's end
     */
    public long getEnd() {
        return this.end;
    }

    /**
     * Gets number of visitors.
     * @return number of visitors
     */
    public int getVisitiorCount() {
        return visitiorCount;
    }

    /**
     * Sets number of visitors.
     * @param visitiorCount number of visitors
     */
    public void setVisitiorCount(int visitiorCount) {
        this.visitiorCount = visitiorCount;
    }

    /**
     * Checks whether visitor's location lays within specified interval.
     * @param visitor specified visitor
     * @return true if visitor's location lays within specified interval
     */
    public boolean contains(Visitor visitor) {
        return this.start < visitor.getLeaving()
                && this.end > visitor.getComing();
    }

    /**
     * Computes the maximum traffic starting with
     * visitor with specified index.
     * @return TrafficInterval which stores maximum traffic according to visitor with specified index
     */
    public TrafficInterval computeTrafficIntervals() {
        Visitor currentVisitor = visitors.get(index);
        for (int i = this.index + 1; i < this.visitors.size(); i++) {
            if (this.contains(visitors.get(i))) {
                if (this.start < visitors.get(i).getComing()) {
                    this.start = visitors.get(i).getComing();
                }
                if (this.getEnd() > visitors.get(i).getLeaving()) {
                    this.end = visitors.get(i).getLeaving();
                }
                this.setVisitiorCount(this.getVisitiorCount() + 1);
            }
        }
        return this;
    }

    @Override
    public TrafficInterval call() throws Exception {
        return this.computeTrafficIntervals();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrafficInterval that = (TrafficInterval) o;

        if (start != that.start) {
            return false;
        }
        if (end != that.end) {
            return false;
        }
        return visitiorCount == that.visitiorCount;
    }

    @Override
    public int hashCode() {
        int result = (int) (start ^ (start >>> 32));
        result = 31 * result + (int) (end ^ (end >>> 32));
        result = 31 * result + visitiorCount;
        return result;
    }
}

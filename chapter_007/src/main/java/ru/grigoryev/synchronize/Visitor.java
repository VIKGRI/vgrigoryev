package ru.grigoryev.synchronize;

/**
 *Class represents visitor in the bank.
 *@author vgrigoryev
 *@since 23.10.2017
 *@version 1
 */
public class Visitor {
    /**
     * Time of coming.
     */
    private long coming;
    /**
     * Time of leaving.
     */
    private long leaving;

    /**
     * Constructor.
     * @param coming Time of coming
     * @param leaving Time of leaving
     */
    public Visitor(long coming, long leaving) {
        if (coming < 0 && leaving < coming) {
            throw new IllegalArrivalException("Invalid time");
        }
        this.coming = coming;
        this.leaving = leaving;
    }

    /**
     * Gets time of coming.
     * @return time of coming
     */
    public long getComing() {
        return coming;
    }

    /**
     * Gets time of leavaing.
     * @return time of leavaing
     */
    public long getLeaving() {
        return leaving;
    }
}

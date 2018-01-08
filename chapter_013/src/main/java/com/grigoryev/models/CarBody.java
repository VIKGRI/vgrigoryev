package com.grigoryev.models;

/**
 * Represents car body in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 07.01.2018
 */
public class CarBody extends Model {

    private static final long serialVersionUID = -1564336663072599659L;
    /**
     * Car body type.
     */
    private String type;
    /**
     * Number of seats.
     */
    private int seatNum;

    /**
     * Constructor.
     */
    public CarBody() {
    }

    /**
     * Constructor.
     * @param id car body's id
     */
    public CarBody(Integer id) {
        super(id);
    }

    /**
     * Gets car body's type.
     * @return car body's type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets car body's type.
     * @param type car body's type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets car body's number of seats.
     * @return number of seats
     */
    public int getSeatNum() {
        return seatNum;
    }

    /**
     * Sets car body's number of seats.
     * @param seatNum number of seats
     */
    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }
}

package com.grigoryev.models;

import java.sql.Timestamp;

/**
 * Item models in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 02.01.2018
 */
public class Item extends Model {

    private static final long serialVersionUID = -7815469742535494172L;
    /**
     * Items's description.
     */
    private String desc;
    /**
     * Item's creation time.
     */
    private Timestamp created;
    /**
     * Item's status.
     */
    private Boolean done;

    /**
     * Constructor.
     */
    public Item() {
        super();
    }

    /**
     * Constructor.
     * @param id items's id.
     */
    public Item(Integer id) {
        super(id);
    }

    /**
     * Gets item's description.
     * @return item's description
     */
    public String getDesc() {
        return this.desc;
    }

    /**
     * Sets item's description.
     * @param desc item's description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Gets creation time.
     * @return creation time
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * Sets creation time.
     * @param created creation time
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * Gets item status.
     * @return true if it's been done and false otherwise
     */
    public Boolean getDone() {
        return done;
    }

    /**
     * Sets item status.
     * @param done true if it's been done and false otherwise
     */
    public void setDone(Boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Item{" + " id=" + this.getId()
                + ", desc='" + desc + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}

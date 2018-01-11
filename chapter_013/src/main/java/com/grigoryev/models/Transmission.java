package com.grigoryev.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents car's transmission in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 07.01.2018
 */
@Entity
@Table(name = "transmissions")
public class Transmission extends Model {

    private static final long serialVersionUID = -1548949299917382700L;

    /**
     * Transmission type.
     */
    private String type;

    /**
     * Number of gears.
     */
    private int gearNum;

    /**
     * Constructor.
     */
    public Transmission() {
    }

    /**
     * Constructor.
     * @param id transmission's id
     */
    public Transmission(Integer id) {
        super(id);
    }

    /**
     * Gets transmission's type.
     * @return transmission's type
     */
    @Column(name = "type")
    public String getType() {
        return type;
    }

    /**
     * Sets transmission's type.
     * @param type transmission's type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets transmission's number of gears.
     * @return number of gears
     */
    @Column(name = "gear_num")
    public int getGearNum() {
        return gearNum;
    }

    /**
     * Sets transmission's number of gears.
     * @param gearNum number of gears
     */
    public void setGearNum(int gearNum) {
        this.gearNum = gearNum;
    }
}

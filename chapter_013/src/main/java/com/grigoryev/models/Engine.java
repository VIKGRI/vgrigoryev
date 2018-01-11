package com.grigoryev.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents car's engine in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 07.01.2018
 */
@Entity
@Table(name = "engines")
public class Engine extends Model {

    private static final long serialVersionUID = 1488002706075047899L;
    /**
     * fuel type used by engine.
     */
    private String fuelType;
    /**
     * volume of engine.
     */
    private double volume;

    /**
     * Constructor.
     */
    public Engine() {
    }

    /**
     * Constructor.
     * @param id engine's id
     */
    public Engine(Integer id) {
        super(id);
    }

    /**
     * Gets fuel type.
     * @return fuel type
     */
    @Column(name = "fuel_type")
    public String getFuelType() {
        return fuelType;
    }

    /**
     * Sets fuel type.
     * @param fuelType fuel type
     */
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    /**
     * Gets engine's volume.
     * @return engine's volume
     */
    @Column(name = "volume")
    public double getVolume() {
        return volume;
    }

    /**
     * Sets engine's volume.
     * @param volume engine's volume
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }
}

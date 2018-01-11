package com.grigoryev.models;

import javax.persistence.*;

/**
 * Represents car in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 07.01.2018
 */
@Entity
@Table(name = "cars")
public class Car extends Model {

    private static final long serialVersionUID = 5832576579145787151L;

    /**
     * Car's model.
     */
    private String model;

    /**
     * Engine set on the car.
     */
    private Engine engine;

    /**
     * Transmission set on the car.
     */
    private Transmission transmission;

    /**
     * Car body of the car.
     */
    private CarBody carBody;

    /**
     * Constructor.
     */
    public Car() {
    }

    /**
     * Constructor.
     * @param id car's id
     */
    public Car(Integer id) {
        super(id);
    }

    /**
     * Gets car's model.
     * @return car's model
     */
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    /**
     * Sets car's model.
     * @param model car's model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets car's engine.
     * @return car's engine
     */
    //@Column(name = "car_engine_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "car_engine_id", nullable = false)
    public Engine getEngine() {
        return engine;
    }

    /**
     * Sets car's engine.
     * @param engine car's engine
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    /**
     * Gets car's transmission.
     * @return car's transmission
     */
    @ManyToOne
    @JoinColumn(name = "car_transmission_id", nullable = false)
    public Transmission getTransmission() {
        return transmission;
    }

    /**
     * Sets car's transmission.
     * @param transmission car's transmission
     */
    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    /**
     * Gets car's car body.
     * @return car's car body
     */
    @ManyToOne
    @JoinColumn(name = "car_body_id", nullable = false)
    public CarBody getCarBody() {
        return carBody;
    }

    /**
     * Sets car's car body.
     * @param carBody  car's car body
     */
    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }
}

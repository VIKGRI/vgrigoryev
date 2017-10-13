package ru.grigoryev.synchronize;

import net.jcip.annotations.ThreadSafe;

/**
 *Class represents model.
 *@author vgrigoryev
 *@since 13.10.2017
 *@version 1
 */
@ThreadSafe
public class Model {
    /**
     * Model's id.
     */
    private int id;
    /**
     * Model's name.
     */
    private String name;
    /**
     * Current version.
     */
    private volatile int version;

    /**
     * Constructor.
     * @param id Model's id
     * @param name Model's name
     * @param version Current version
     */
    public Model(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    /**
     * Version's getter.
     * @return current version
     */
    public synchronized int getVersion() {
        return this.version;
    }
    /**
     * Id's getter.
     * @return model's id
     */
    public synchronized int getId() {
        return this.id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Model model = (Model) o;

        return id == model.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

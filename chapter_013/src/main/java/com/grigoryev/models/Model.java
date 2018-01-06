package com.grigoryev.models;

import java.io.Serializable;

/**
 * Model in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 02.01.2018
 */
public class Model implements Serializable {

    private static final long serialVersionUID = 7435861072540085163L;
    /**
     * Model id in the database.
     */
    private Integer id;

    /**
     * Constructor.
     */
    public Model() {

    }

    /**
     * Constructor.
     * @param id models id
     */
    public Model(Integer id) {
        this.id = id;
    }

    /**
     * Gets id.
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}

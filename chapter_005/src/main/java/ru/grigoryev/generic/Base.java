package ru.grigoryev.generic;

/**
 * Abstract class represents the element which can be stored.
 *
 * @author vgrigoryev
 * @version 1
 * @since 23.09.2017
 */
public abstract class Base {
    /**
     *Id of the element stored in the storage.
     */
    private String id = "";

    /**
     * Constructor.
     * @param id specified id
     */
    public Base(String id) {
        if (id != null) {
            this.id = id;
        }
    }

    /**
     * Id's getter.
     * @return element's id.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Id's setter.
     * @param id specified id of the element
     */
    public void setId(String id) {
        if (id != null) {
            this.id = id;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Base base = (Base) o;

        return id != null ? id.equals(base.id) : base.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

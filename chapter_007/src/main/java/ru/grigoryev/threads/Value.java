package ru.grigoryev.threads;

/**
 *Class integer value.
 *@author vgrigoryev
 *@since 06.10.2017
 *@version 1
 */
public class Value {
    /**
     * Primitive integer value wrapped in the object.
     */
    private int value;

    /**
     * Constructor.
     * @param value integer value to store inside
     */
    public Value(int value) {
        if (value > 0) {
            this.value = value;
        } else {
            this.value = 0;
        }
    }

    /**
     * Increments value.
     */
    public void incrementValue() {
        this.value++;
    }

    /**
     * Value's getter.
     * @return value
     */
    public int getValue() {
        return this.value;
    }
}

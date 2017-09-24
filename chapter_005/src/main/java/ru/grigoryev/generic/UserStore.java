package ru.grigoryev.generic;

/**
 * Storage of elements of type User.
 *
 * @author vgrigoryev
 * @version 1
 * @since 23.09.2017
 */
public class UserStore extends AbstractStore<User> {

    /**
     * Constructor.
     * @param size size of array.
     */
    public UserStore(int size) {
        super(size);
    }
}

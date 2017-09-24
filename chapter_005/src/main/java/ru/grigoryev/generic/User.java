package ru.grigoryev.generic;
/**
 * Class represents the element which can be stored.
 *
 * @author vgrigoryev
 * @version 1
 * @since 23.09.2017
 */
public class User extends Base {
    /**
     * User's name.
     */
    private String name = "";
    /**
     * Constructor.
     * @param id specified id
     * @param name specified name
     */
    public User(String id, String name) {
        super(id);
        if (name != null) {
            this.name = name;

        }
    }

    /**
     * User's name getter.
     * @return User's name
     */
    public String getName() {
        return this.name;
    }
}

package ru.grigoryev.maps;

import java.util.Calendar;
import java.util.Objects;

/**
 * User.
 *
 * @author vgrigoryev
 * @version 1
 * @since 27.09.2017
 */
public class User {
    /**
     * User's name.
     */
    private String name = "";
    /**
     * Amount of User's children.
     */
    private int children = 0;
    /**
     * User's birthday.
     */
    private Calendar birthday;

    /**
     * Constructor.
     *
     * @param name     User's birthday
     * @param children Amount of User's children
     * @param birthday User's birthday
     */
    public User(String name, int children, Calendar birthday) {
        if (name != null) {
            this.name = name;
        }
        if (children > 0) {
            this.children = children;
        }
        this.birthday = birthday;
    }

    /**
     * User's name getter.
     *
     * @return User's name
     */
    public String getName() {
        return name;
    }

    /**
     * User's children amount getter.
     *
     * @return number of children
     */
    public int getChildren() {
        return children;
    }

    /**
     * User's birthday getter.
     * @return User's birthday
     */
    public Calendar getBirthday() {
        return birthday;
    }

    /**
     * User's name setter.
     * @param name User's name
     */
    public void setName(String name) {
        if (this.name != null) {
            this.name = name;
        }
    }

    /**
     * User's children amount setter.
     * @param children number of children
     */
    public void setChildren(int children) {
        this.children = children;
    }

    /**
     * User's birthday setter.
     * @param birthday User's birthday
     */
    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + Objects.hashCode(this.name);
        result = 31 * result + this.children;
        result = 31 * result + Objects.hashCode(this.birthday);
        return result;
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", children=" + children
                + '}';
    }
}

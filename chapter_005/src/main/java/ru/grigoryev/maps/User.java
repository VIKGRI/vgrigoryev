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
    private String name;
    /**
     * Amount of User's children.
     */
    private int children;
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
        } else {
            throw new NullPointerException();
        }
        if (children > 0) {
            this.children = children;
        } else {
            this.children = 0;
        }
        if (birthday == null) {
            throw new NullPointerException();
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
        if (children > 0) {
            this.children = children;
        }
    }

    /**
     * User's birthday setter.
     * @param birthday User's birthday
     */
    public void setBirthday(Calendar birthday) {
        if (birthday != null) {
            this.birthday = birthday;
        }
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
    public boolean equals(Object otherObject) {
        if (otherObject == this) {
            return true;
        }
        if (!(otherObject instanceof User)) {
            return false;
        }
        User other = (User) otherObject;

        return Objects.equals(this.name, other.name)
                && this.children == other.children
                && Objects.equals(this.birthday, other.birthday);
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", children=" + children
                + '}';
    }
}

package ru.grigoryev.maps;

import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for learning usage of equals and hashCode methods.
 *
 * @author vgrigoryev
 * @since 27.09.2017
 */
public class UserTest {
    /**
     * equals and hashCode are not overriden in User class.
     */
    @Test
    public void whenNoOverrideEqualsAndNoOverrideHashcodeThenEqualUsersAreAdded() {
        Map<User, Object> users = new HashMap<>();

        User first = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));
        User second = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));

        users.put(first, 1);
        users.put(second, 2);
        System.out.println(users);
    }
    /**
     * equals is not overriden and hashCode is overriden in User class.
     */
    @Test
    public void whenNoOverrideEqualsAndHashcodeIsOverridenThenEqualUsersAreAdded() {
        Map<User, Object> users = new HashMap<>();

        User first = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));
        User second = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));

        users.put(first, 1);
        users.put(second, 2);
        System.out.println(users);
    }

}
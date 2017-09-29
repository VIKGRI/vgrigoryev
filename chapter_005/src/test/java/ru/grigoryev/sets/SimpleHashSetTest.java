package ru.grigoryev.sets;

import org.junit.Test;
import ru.grigoryev.maps.User;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing SimpleHashSet's methods.
 *
 * @author vgrigoryev
 * @since 29.09.2017
 */
public class SimpleHashSetTest {
    /**
     * insert() method testing.
     */
    @Test
    public void whenAddEqualKeysThenOnlyUniqueUsersAreAdded() {
        SimpleHashSet<User> users = new SimpleHashSet<>();
        User first = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));
        User second = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));

        users.insert(first);
        users.insert(second);
        int result = users.size();
        int expect = 1;

        assertThat(result, is(expect));
    }
    /**
     * delete() method testing.
     */
    @Test
    public void whenDeleteUserThenSizeDecreases() {
        SimpleHashSet<User> users = new SimpleHashSet<>();
        User first = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));
        User second = new User("Alex", 7, new GregorianCalendar(1983, 11, 2));

        users.insert(first);
        users.insert(second);
        System.out.println(users);
        users.delete(second);
        int result = users.size();
        int expect = 1;

        assertThat(result, is(expect));
    }
    /**
     * Testing next() method.
     */
    @Test
    public void whenGetNextCallThenNumbersMatch() {
        SimpleHashSet<User> users = new SimpleHashSet<>(50);
        User first = new User("Sergey", 2, new GregorianCalendar(1989, 6, 12));
        User second = new User("Greg", 3, new GregorianCalendar(1981, 3, 20));
        User third = new User("Alexey", 7, new GregorianCalendar(1975, 11, 1));
        User forth = new User("Nick", 11, new GregorianCalendar(2000, 9, 13));
        User fifth = new User("Sam", 13, new GregorianCalendar(1966, 5, 17));

        users.insert(first);
        users.insert(second);
        users.insert(third);
        users.insert(forth);
        users.insert(fifth);
        Iterator<User> it = users.iterator();
        Set<User> result = new HashSet<>();
        while (it.hasNext()) {
            result.add(it.next());
        }
        Set<User> expect = new HashSet<>();
        expect.add(first);
        expect.add(second);
        expect.add(third);
        expect.add(forth);
        expect.add(fifth);

        assertThat(result, is(expect));
    }
    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        SimpleHashSet<User> users = new SimpleHashSet<>(100);
        User first = new User("Sergey", 2, new GregorianCalendar(1989, 6, 12));
        User second = new User("Greg", 3, new GregorianCalendar(1981, 3, 20));
        users.insert(first);
        users.insert(second);

        Iterator<User> it = users.iterator();
        it.next();
        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }
}
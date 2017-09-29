package ru.grigoryev.maps;

import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing Directory's methods.
 *
 * @author vgrigoryev
 * @since 29.09.2017
 */
public class DirectoryTest {
    /**
     * insert() method testing.
     */
    @Test
    public void whenAddEqualKeysThenOnlyUniqueUsersAreAdded() {
        Directory<User, Integer> users = new Directory<>();

        User first = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));
        User second = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));

        users.insert(first, 1);
        users.insert(second, 2);
        int result = users.size();
        int expect = 1;

        assertThat(result, is(expect));
        System.out.println(users);
    }

    /**
     * get() method testing.
     */
    @Test
    public void whenGetByKeyThenValueMatches() {
        Directory<User, Integer> users = new Directory<>();

        User first = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));
        User second = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));
        User third = new User("Alex", 7, new GregorianCalendar(1983, 11, 2));
        User forth = new User("Sam", 10, new GregorianCalendar(1983, 11, 2));
        User fifth = new User("Sam", 10, new GregorianCalendar(1983, 11, 2));

        users.insert(first, 1);
        users.insert(second, 2);
        users.insert(third, 3);
        users.insert(forth, 4);
        users.insert(fifth, 5);

        Integer result = users.get(forth);
        Integer expect = 5;

        assertThat(result, is(expect));
    }
    /**
     * delete() method testing.
     */
    @Test
    public void whenDeleteUserThenSizeDecreases() {
        Directory<User, Integer> users = new Directory<>();

        User first = new User("Sergey", 2, new GregorianCalendar(1983, 11, 2));
        User second = new User("Alex", 7, new GregorianCalendar(1983, 11, 2));

        users.insert(first, 1);
        users.insert(second, 2);

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
        Directory<User, Integer> users = new Directory<>(100);

        User first = new User("Sergey", 2, new GregorianCalendar(1989, 6, 12));
        User second = new User("Greg", 3, new GregorianCalendar(1981, 3, 20));
        User third = new User("Alexey", 7, new GregorianCalendar(1975, 11, 1));
        User forth = new User("Nick", 11, new GregorianCalendar(2000, 9, 13));
        User fifth = new User("Sam", 13, new GregorianCalendar(1966, 5, 17));

        users.insert(first, 1);
        users.insert(second, 2);
        users.insert(third, 3);
        users.insert(forth, 4);
        users.insert(fifth, 5);

        Iterator<Pair<User, Integer>> it = users.iterator();
        Set<Pair<User, Integer>> result = new HashSet<>();
        while (it.hasNext()) {
            result.add(it.next());
        }

        Set<Pair<User, Integer>> expect = new HashSet<>();
        expect.add(new Pair<>(first, 1));
        expect.add(new Pair<>(second, 2));
        expect.add(new Pair<>(third, 3));
        expect.add(new Pair<>(forth, 4));
        expect.add(new Pair<>(fifth, 5));

        assertThat(result, is(expect));
    }
    /**
     * Testing hasNext() method.
     */
    @Test
    public void whenCheckNextPositionThenReturnConstantValue() {
        Directory<User, Integer> users = new Directory<>(100);

        User first = new User("Sergey", 2, new GregorianCalendar(1989, 6, 12));
        User second = new User("Greg", 3, new GregorianCalendar(1981, 3, 20));

        users.insert(first, 1);
        users.insert(second, 2);

        Iterator<Pair<User, Integer>> it = users.iterator();
        it.next();
        it.next();
        it.hasNext();
        boolean result = it.hasNext();

        assertThat(result, is(false));
    }
}
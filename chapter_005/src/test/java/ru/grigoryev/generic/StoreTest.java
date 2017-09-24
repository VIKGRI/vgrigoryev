package ru.grigoryev.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class for testing SimpleArrayTest class's methods.
 * @author vgrigoryev
 * @since 22.09.2017
 */
public class StoreTest {
    /**
     * Testing add() method.
     */
    @Test
    public void whenAddUserStoreThenStoreContainsIt() {
        Store<User> us = new UserStore(5);
        us.add(new User("125", "Ivan Petrov"));
        boolean result = us.contains(new User("125", "Ivan Petrov"));
                assertThat(result, is(true));
    }
    /**
     * Testing delete() method.
     */
    @Test
    public void whenDeleteRoleStoreThenStoreDoesNotContainIt() {
        Store<Role> rs = new RoleStore(5);
        rs.add(new Role("125"));
        rs.add(new Role("127"));
        boolean beforeDelete = rs.contains(new Role("125"));
        rs.delete(new Role("125"));
        boolean afterDelete = rs.contains(new Role("125"));
        boolean expect = beforeDelete && !afterDelete;
        assertThat(expect, is(true));
    }
    /**
     * Testing update() method.
     */
    @Test
    public void whenUpdateUserThenStoreContainUpdated() {
        Store<User> us = new UserStore(5);
        us.add(new User("125", "Ivan Petrov"));
        boolean afterUpdate = us.update(new User("125", "Ivan Ivanov"));
        assertThat(afterUpdate, is(true));
    }
}
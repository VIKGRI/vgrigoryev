package ru.job4j.generics;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class UserConvert testing.
*@author vgrigoryev
*@since 16.09.2017
*@version 1
*/
public class SortUserTest {
	/**
	*method for sortUser method testing.
	*/
	@Test
	public void whenPassListOfUsersThenSortedByAgeTreeSetReturns() {
		SortUser sortUsers = new SortUser();
		List<User> usersList = new ArrayList<>();

		usersList.add(new User(1, "Ann", 21, "Rostov"));
		usersList.add(new User(2, "John", 23, "New York"));
		usersList.add(new User(3, "Peter", 52, "Samara"));
		usersList.add(new User(4, "Alex", 46, "Moscow"));
		usersList.add(new User(5, "Mary", 33, "London"));
		Set<User> expectSet = new LinkedHashSet<User>();
		expectSet.add(new User(1, "Ann", 21, "Rostov"));
		expectSet.add(new User(2, "John", 23, "New York"));
		expectSet.add(new User(5, "Mary", 33, "London"));
		expectSet.add(new User(4, "Alex", 46, "Moscow"));
		expectSet.add(new User(3, "Peter", 52, "Samara"));

		Set<User> resultSet = sortUsers.sort(usersList);
		assertThat(resultSet, is(expectSet));
	 }
	/**
	 *method for sortUser method testing.
	 */
	@Test
	public void whenPassListOfUsersThenSortedByNameLengthListReturns() {
		SortUser sortUsers = new SortUser();
		List<User> usersList = new ArrayList<>();

		usersList.add(new User(2, "Ted", 23, "New York"));
		usersList.add(new User(1, "Ann", 21, "Rostov"));
		usersList.add(new User(3, "Peter", 52, "Samara"));
		usersList.add(new User(4, "Alex", 46, "Moscow"));
		usersList.add(new User(5, "Mary", 33, "London"));
		List<User> expectList = new ArrayList<User>();
		expectList.add(new User(1, "Ann", 21, "Rostov"));
		expectList.add(new User(2, "Ted", 23, "New York"));
		expectList.add(new User(4, "Alex", 46, "Moscow"));
		expectList.add(new User(5, "Mary", 33, "London"));
		expectList.add(new User(3, "Peter", 52, "Samara"));
		List<User> resultList = sortUsers.sortNameLength(usersList);
		assertThat(resultList, is(expectList));
	}
	/**
	 *method for sortUser method testing.
	 */
	@Test
	public void whenPassListOfUsersThenSortedByAllFieldsListReturns() {
		SortUser sortUsers = new SortUser();
		List<User> usersList = new ArrayList<>();

		usersList.add(new User(2, "Ted", 23, "New York"));
		usersList.add(new User(1, "Ann", 21, "Rostov"));
		usersList.add(new User(3, "Ted", 52, "Samara"));
		usersList.add(new User(4, "Alex", 46, "Moscow"));
		usersList.add(new User(5, "Alex", 33, "London"));
		List<User> expectList = new ArrayList<User>();
		expectList.add(new User(5, "Alex", 33, "London"));
		expectList.add(new User(4, "Alex", 46, "Moscow"));
		expectList.add(new User(1, "Ann", 21, "Rostov"));
		expectList.add(new User(2, "Ted", 23, "New York"));
		expectList.add(new User(3, "Ted", 52, "Samara"));
		List<User> resultList = sortUsers.sortByAllFields(usersList);
		assertThat(resultList, is(expectList));
	}
}
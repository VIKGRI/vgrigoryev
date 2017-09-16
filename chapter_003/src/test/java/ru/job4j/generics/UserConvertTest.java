package ru.job4j.generics;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class UserConvert testing.
*@author vgrigoryev
*@since 15.09.2017
*@version 1
*/
public class UserConvertTest {
	/**
	*method for process method testing.
	*/
	@Test
	public void whenConvertListToMapThenMapsMatch() {
		UserConvert userConvert = new UserConvert();
		List<User> usersList = new ArrayList<>();
		usersList.add(new User(1, "Ann", 21, "Rostov"));
		usersList.add(new User(2, "John", 23, "New York"));
		usersList.add(new User(3, "Peter", 52, "Samara"));
		usersList.add(new User(4, "Alex", 46, "Moscow"));
		usersList.add(new User(5, "Mary", 33, "London"));
		HashMap<Integer, User> expectMap = new HashMap<Integer, User>();
		for (User user : usersList) {
			expectMap.put(user.getId(), user);
		}
		HashMap<Integer, User> resultMap = userConvert.process(usersList);
		assertThat(resultMap, is(expectMap));
	 }
}
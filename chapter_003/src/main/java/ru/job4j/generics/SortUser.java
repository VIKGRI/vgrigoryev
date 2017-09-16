package ru.job4j.generics;

import java.util.Set;
import java.util.TreeSet;
import java.util.List;

/**
*Class sorting collection of users.
*@author vgrigoryev
*@since 16.09.2017
*@version 1
*/
public class SortUser {
	/**
	*This method provides sorting list of users by inserting it into map.
	*@param list list of users
	*@return treeset of users
	*/
	public Set<User> sort(List<User> list) {
		Set<User> sortedUsers = new TreeSet<>();
		if (list != null) {
			for (User user : list) {
				sortedUsers.add(user);
			}
			return sortedUsers;
		}
		return  sortedUsers;
	}
}
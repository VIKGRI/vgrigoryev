package ru.job4j.generics;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	/**
	 *This method provides sorting list of users by inserting it into map.
	 *@param list list of users
	 *@return sorted by name length list of users
	 */
	public List<User> sortNameLength(List<User> list)  {
		if (list == null) {
			throw new NullPointerException();
		}
		Collections.sort(list, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return (o1.getName().length() - o2.getName().length()) != 0
						? o1.getName().length() - o2.getName().length()
						: o1.getName().compareTo(o2.getName());
			}
		});
		return  list;
	}
	/**
	 *This method provides sorting list of users by inserting it into map.
	 *@param list list of users
	 *@return sorted by name length list of users
	 */
	public List<User> sortByAllFields(List<User> list)  {
		if (list == null) {
			throw new NullPointerException();
		}
		Collections.sort(list, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				return o1.getName().compareTo(o2.getName()) != 0
						? o1.getName().compareTo(o2.getName())
						: Integer.compare(o1.getAge(), o2.getAge());
			}
		});
		return  list;
	}
}
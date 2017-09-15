package ru.job4j.generics;

import java.util.HashMap;
import java.util.List;

/**
*Class converting list of Users into map.
*@author vgrigoryev
*@since 15.09.2017
*@version 1
*/
public class UserConvert {
	/**
	*This method provides converting list of users into map.
	*@param list list of users
	*@return map of users with id as a key
	*/
	public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result =  new HashMap<>();
        for (User user : list) {
            result.put(user.getId(), user);
        }
        return result;
    }
}
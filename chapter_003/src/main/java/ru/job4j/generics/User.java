package ru.job4j.generics;

/**
*Class describes user.
*@author vgrigoryev
*@since 15.09.2017
*@version 1
*/
public class User implements Comparable<User> {
	/**
	*User's id.
	*/
	private int id;
	/**
	*User's name.
	*/
	private String name;
	/**
	*User's age.
	*/
	private int age;
	/**
	 *City where user from.
	 */
	private String city;
	/**
	*Constructor with parameters.
	*@param id User's id
	*@param name User's name
	 *@param age User's age
	*@param city City where user from
	*/
	public User(int id, String name, int age, String city) {
		this.id = id > 0 ? id : 0;
		if (name != null) {
			this.name = name;
		} else {
			this.name = "no name";
		}
		if (age > 0) {
			this.age = age;
		} else {
			this.age = 0;
		}
		if (city != null) {
			this.city = city;
		} else {
			this.city = "unknown";
		}
	}
	/**
	*This method provides getting id.
	*@return user's id.
	*/
	int getId() {
		return this.id;
	}
	/**
	*This method provides getting name.
	*@return User's name.
	*/
	String getName() {
		return this.name;
	}
	/**
	*This method provides setting name.
	*@param name User's name
	*/
	void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}
	/**
	 *This method provides getting age.
	 *@return User's age.
	 */
	int getAge() {
		return this.age;
	}
	/**
	 *This method provides setting age.
	 *@param age User's age
	 */
	void setName(int age) {
		if (age > 0 && age > this.age) {
			this.age = age;
		}
	}
	/**
	*This method provides getting city.
	*@return User's city.
	*/
	String getCity() {
		return this.city;
	}
	/**
	*This method provides setting city.
	*@param city User's city
	*/
	void setCity(String city) {
		if (city != null) {
			this.city = city;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		User user = (User) o;

		if (id != user.id) {
			return false;
		}
		if (name != null ? !name.equals(user.name) : user.name != null) {
			return false;
		}
		return city != null ? city.equals(user.city) : user.city == null;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(User o) {
		return Integer.compare(this.age, o.age) == 0 ? this.name.compareTo(o.name) : Integer.compare(this.age, o.age);
	}
}
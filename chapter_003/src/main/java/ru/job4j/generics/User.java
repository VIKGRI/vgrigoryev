package ru.job4j.generics;

/**
*Class describes user.
*@author vgrigoryev
*@since 15.09.2017
*@version 1
*/
public class User {
	/**
	*User's id.
	*/
	private int id;
	/**
	*User's name.
	*/
	private String name;
	/**
	*City where user from.
	*/
	private String city;
	/**
	*Constructor with parameters.
	*@param id User's id
	*@param name User's name
	*@param city City where user from
	*/
	public User(int id, String name, String city) {
		this.id = id > 0 ? id : 0;
		if (name != null) {
			this.name = name;
		} else {
			this.name = "no name";
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
	*This method provides getting id.
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
}
package ru.job4j.professions;

/**
*Class represent the profession.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class Profession {
	/**
	*This field holds the name of person.
	*/
	private String name;
	/**
	*This field holds the information about diploma.
	*/
	private String diploma;
	/**
	*This field holds the information about expirience which is represented in days.
	*/
	private double experience;
	/**
	*Default constructor.
	*/
	public Profession() {
		this.name = "";
		this.diploma = "";
	}
	/**
	*Constructor with parameters.
	*@param name Name of person
	*@param diploma Information about diploma
	*@param experience Days of experience
	*/
	public Profession(String name, String diploma, double experience) {
		if (name != null && diploma != null && experience >= 0) {
			this.name = name;
			this.diploma = diploma;
			this.experience = experience;
		} else {
			this.name = "";
			this.diploma = "";
		}
	}
	/**
	*This method is used for setting name.
	*@param name Name of person
	*/
	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}
	/**
	*This method is used for getting name.
	*@return String name
	*/
	public String getName() {
		return this.name;
	}
	/**
	*This method is used for setting diploma.
	*@param diploma Desdription of diploma
	*/
	public void setDiploma(String diploma) {
		if (diploma != null) {
			this.diploma = diploma;
		}
	}
	/**
	*This method is used for getting diploma.
	*@return diploma
	*/
	public String getDiploma() {
		return this.diploma;
	}
	/**
	*This method is used for setting value of experience.
	*@param experience Days of experience
	*/
	public void setExperience(double experience) {
		if (experience >= this.experience) {
			this.experience = experience;
		}
	}
	/**
	*This method is used for getting value of experience.
	*@return experience Days of experience
	*/
	public double getExperience() {
		return this.experience;
	}
}
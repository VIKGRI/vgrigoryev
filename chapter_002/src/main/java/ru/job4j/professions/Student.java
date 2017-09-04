package ru.job4j.professions;

/**
*Class represent the student.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class Student {
	/**
	*This field holds the name of person.
	*/
	private String name = "";
	/**
	*This field holds the knowledge.
	*/
	private Knowledge knowledge = new Knowledge();
	/**
	*This method is used for setting name.
	*@param name Name of student
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
	*This method is used for setting knowledge data.
	*@param knowledge Knowledge object
	*@return Returns true if the knowledge is updated, and false otherwise
	*/
	public boolean acceptKnowledge(Knowledge knowledge) {
		if (knowledge != null) {
			this.knowledge.add(knowledge);
			return true;
		}
		return false;
	}
}
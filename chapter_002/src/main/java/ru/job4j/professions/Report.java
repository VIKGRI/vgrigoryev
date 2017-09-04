package ru.job4j.professions;

/**
*Class represent the report.
*@author vgrigoryev
*@since 04.09.2017
*@version 1
*/
public class Report {
	/**
	*This field holds the description of generated report.
	*/
	private String description = "";
	/**
	*Constructor with parameters.
	*@param name Name of person
	*@param id task id
	*/
	public Report(String name, int id) {
		this.description = name + " решил задачу № " + id;
	}
	/**
	*This method is used for getting report description.
	*@return report description
	*/
	public String getDescription() {
		return this.description;
	}
}
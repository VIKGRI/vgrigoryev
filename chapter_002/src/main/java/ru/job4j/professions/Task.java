package ru.job4j.professions;

/**
*Class represent the task.
*@author vgrigoryev
*@since 04.09.2017
*@version 1
*/
public class Task {
	/**
	*This field holds the task id.
	*/
	private int id;
	/**
	*This field holds the task description.
	*/
	private String description = "";
	/**
	*This field holds the task deadline.
	*/
	private String deadline = "";
	/**
	*This method is used for setting task id.
	*@param id task id
	*/
	public void setId(int id) {
		this.id = id;
	}
	/**
	*This method is used for getting task id.
	*@return task id
	*/
	public int getId() {
		return this.id;
	}
	/**
	*This method is used for setting description.
	*@param description task description
	*/
	public void setDescription(String description) {
		if (description != null) {
			this.description = description;
		}
	}
	/**
	*This method is used for getting task description.
	*@return task description
	*/
	public String getDescription() {
		return this.description;
	}
	/**
	*This method is used for setting deadline.
	*@param deadline task deadline
	*/
	public void setDeadline(String deadline) {
		if (deadline != null) {
			this.deadline = deadline;
		}
	}
	/**
	*This method is used for getting task deadline.
	*@return task deadline
	*/
	public String getDeadline() {
		return this.deadline;
	}
	/**
	*This method is used for generating report.
	*@param name Engineer's name
	*@return Object of Report class
	*/
	public Report generateReport(String name) {
		if (name != null) {
			return new Report(name, id);
		}
		return new Report("no name", id);
	}
}
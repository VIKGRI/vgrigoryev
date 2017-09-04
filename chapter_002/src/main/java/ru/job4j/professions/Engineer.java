package ru.job4j.professions;

/**
*Class represent the engineeer.
*@author vgrigoryev
*@since 04.09.2017
*@version 1
*/
public class Engineer extends Profession {
		/**
	*This field holds the current Task.
	*/
	private Task currentTask;
	/**
	*This field holds the domain of engineer.
	*/
	private String domain;
	/**
	*Default constructor.
	*/
	public Engineer() {
		super();
		currentTask = new Task();
		domain = "";
	}
	/**
	*Constructor with parameters.
	*@param name Name of person
	*@param diploma Information about diploma
	*@param experience Days of experience
	*@param task task to solve
	*@param domain Engineer's domain
	*/
	public Engineer(String name, String diploma, double experience, Task task, String domain) {
		super(name, diploma, experience);
		this.currentTask = task;
		this.domain = domain;
	}
	/**
	*This method is used for performing task.
	*@param task Task to solve
	*@return Object of Report class
	*/
	public Report perform(Task task) {
		this.currentTask = task;
		return task.generateReport(this.getName());
	}
	/**
	*This method is used for setting current task.
	*@param task Object of Task class
	*/
	public void setCurrentTask(Task task) {
		if (task != null) {
			this.currentTask = task;
		}
	}
	/**
	*This method is used for getting current task.
	*@return Object of Task class
	*/
	public Task getCurrentTask() {
		return this.currentTask;
	}
		/**
	*This method is used for setting domain.
	*@param domain Description of domain
	*/
	public void setDomain(String domain) {
		if (domain != null) {
			this.domain = domain;
		}
	}
	/**
	*This method is used for getting domain.
	*@return domain
	*/
	public String getDomain() {
		return this.domain;
	}
}
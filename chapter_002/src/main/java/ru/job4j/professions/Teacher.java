package ru.job4j.professions;

/**
*Class represent the teacher.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class Teacher extends Profession {
	/**
	*This field holds the knowledge.
	*/
	private Knowledge knowledge;
	/**
	*Default constructor.
	*/
	public Teacher() {
		super();
		knowledge = new Knowledge();
	}
	/**
	*Constructor with parameters.
	*@param name Name of person
	*@param diploma Information about diploma
	*@param experience Days of experience
	*@param knowledge Object of Knowledge class
	*/
	public Teacher(String name, String diploma, double experience, Knowledge knowledge) {
		super(name, diploma, experience);
		this.knowledge = knowledge;
	}
	/**
	*This method is used for teaching.
	*@param student Student who gets knowledge
	*@return String description of teaching process
	*/
	public String teach(Student student) {
		if (student.acceptKnowledge(this.knowledge)) {
			return this.getName() + " учит " + student.getName();
		} else {
			return "студент провалился";
		}
	}
	/**
	*This method is used for getting new knowledges.
	*@param knowledge Object of Knowledge class
	*/
	public void improveSkills(Knowledge knowledge) {
		if (knowledge != null) {
			this.knowledge.add(knowledge);
		}
	}
	/**
	*This method is used for getting knowledge data.
	*@return knowledge knowledge
	*/
	public Knowledge getKnowledge() {
		return this.knowledge;
	}
}
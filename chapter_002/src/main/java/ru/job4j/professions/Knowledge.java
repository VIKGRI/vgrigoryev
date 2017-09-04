package ru.job4j.professions;

import java.util.ArrayList;

/**
*Class represent the knowledge.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class Knowledge {
	/**
	*This field holds the array of subjects to learn.
	*/
	private ArrayList<String> subjects = new ArrayList<String>();
	/**
	*This method is used for adding subject.
	*@param subject learned subject
	*/
	public void addSubject(String subject) {
		if (subject != null) {
			this.subjects.add(subject);
		}
	}
	/**
	*This method is used for getting subjects.
	*@return list of subjects
	*/
	public ArrayList<String> getSubjects() {
		return this.subjects;
	}
	/**
	*This method is used for adding new knowledges to the end of the existing list.
	*@param knowledge Knowledge object
	*/
	public void add(Knowledge knowledge) {
		if (knowledge != null) {
			this.subjects.addAll(knowledge.subjects);
		}
	}
}
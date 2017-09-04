package ru.job4j.professions;

/**
*Class represent the patient.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class Patient {
	/**
	*This field holds the name of person.
	*/
	private String name = "";
	/**
	*This field holds the treatment to take.
	*/
	private Treatment treatment = new Treatment();
	/**
	*This method is used for setting name.
	*@param name Name of patient
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
	*This method is used for setting treatment data.
	*@param treatment Treatment object
	*@return Returns true if the treatment is taken, and false otherwise
	*/
	public boolean acceptTreatment(Treatment treatment) {
		if (treatment != null) {
			this.treatment.modifyTreatment(treatment);
			return true;
		}
		return false;
	}
}
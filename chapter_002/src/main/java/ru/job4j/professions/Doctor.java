package ru.job4j.professions;

/**
*Class represent the doctor.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class Doctor extends Profession {
		/**
	*This field holds the treatment.
	*/
	private Treatment treatment;
	/**
	*Default constructor.
	*/
	public Doctor() {
		super();
		treatment = new Treatment();
	}
	/**
	*Constructor with parameters.
	*@param name Name of person
	*@param diploma Information about diploma
	*@param experience Days of experience
	*@param treatment Object of Treatment class
	*/
	public Doctor(String name, String diploma, double experience, Treatment treatment) {
		super(name, diploma, experience);
		this.treatment = treatment;
	}
	/**
	*This method is used for healing.
	*@param patient Patient who gets treatment
	*@return String description of healing process
	*/
	public String heal(Patient patient) {
		if (patient.acceptTreatment(this.treatment)) {
			return this.getName() + " лечит " + patient.getName();
		} else {
			return "больной не выздоровел";
		}
	}
	/**
	*This method is used for getting new treatments.
	*@param treatment Object of Treatment class
	*/
	public void getTreatments(Treatment treatment) {
		if (treatment != null) {
			this.treatment.modifyTreatment(treatment);
		}
	}
	/**
	*This method is used for getting treatment data.
	*@return treatment treatment
	*/
	public Treatment getTreatment() {
		return this.treatment;
	}
}
package ru.job4j.professions;



/**
*Class represent the treatment.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class Treatment {
	/**
	*This field holds the drug to take.
	*/
	private Drug drug = new Drug();
	/**
	*This field holds the drug to take.
	*/
	private MedicalProcedure medProcedure = new MedicalProcedure();
	/**
	*This method is used for setting drug.
	*@param drugName drug name
	*@param id drug id
	*/
	public void setDrug(String drugName, String id) {
		if (drugName != null && id != null) {
			this.drug.setId(id);
			this.drug.setName(drugName);
		}
	}
	/**
	*This method is used for setting medical procedure.
	*@param name drug name
	*@param amount amount
	*/
	public void setProcedure(String name, int amount) {
		if (name != null && amount > 0) {
			this.medProcedure.setAmount(amount);
			this.medProcedure.setName(name);
		}
	}
	/**
	*This method is used for getting drug data.
	*@return Drug object
	*/
	public Drug getDrug() {
		return this.drug;
	}
		/**
	*This method is used for getting medical procedure data.
	*@return MedicalProcedure object
	*/
	public MedicalProcedure getMedicalProcedure() {
		return this.medProcedure;
	}
	/**
	*This method is used for adding new knowledges to the end of the existing list.
	*@param treatment Treatment object
	*/
	public void modifyTreatment(Treatment treatment) {
		if (treatment != null) {
			this.drug = treatment.drug;
			this.medProcedure = treatment.medProcedure;
		}
	}
}
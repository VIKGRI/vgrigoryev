package ru.job4j.professions;

/**
*Class represent the medical procedure.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class MedicalProcedure {
	/**
	*This field holds the amount of procedures.
	*/
	private int amount;
	/**
	*This field holds the procedure name.
	*/
	private String name = "";
	/**
	*This method is used for setting amount.
	*@param amount amount of procedures
	*/
	public void setAmount(int amount) {
		if (amount > 0) {
			this.amount = amount;
		}
	}
	/**
	*This method is used for setting procedure name.
	*@param name procedure name
	*/
	public void setName(String name) {
		if (name != null) {
			this.name = name;
		}
	}
}
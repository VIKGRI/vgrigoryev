package ru.job4j.professions;

/**
*Class represent the drug.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class Drug {
	/**
	*This field holds the drug id.
	*/
	private String id = "";
	/**
	*This field holds the drug name.
	*/
	private String name = "";
	/**
	*This method is used for setting drug id.
	*@param drugId drug id
	*/
	public void setId(String drugId) {
		if (id != null) {
			this.id = id;
		}
	}
	/**
	*This method is used for setting drug name.
	*@param drugName drug name
	*/
	public void setName(String drugName) {
		if (drugName != null) {
			this.name = drugName;
		}
	}
}
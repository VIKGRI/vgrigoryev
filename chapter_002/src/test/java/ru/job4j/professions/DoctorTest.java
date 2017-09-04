package ru.job4j.professions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class Doctor testing.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class DoctorTest {
	/**
	*method for heal method testing.
	*/
	@Test
	public void whenDoctorHealThenCorrectDescription() {
		Treatment treatment = new Treatment();
		treatment.setDrug("Аспирин", "125-6");
		treatment.setProcedure("Массаж", 10);
		Doctor doctor = new Doctor("Алексей Петрович", "СПбГУ", 3650, treatment);
		Patient patient = new Patient();
		patient.setName("Антон");
		String result = doctor.heal(patient);
		String expectString = "Алексей Петрович лечит Антон";
		assertThat(result, is(expectString));
		}
}
package ru.job4j.professions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class Engineer testing.
*@author vgrigoryev
*@since 04.09.2017
*@version 1
*/
public class EngineerTest {
	/**
	*method for perform method testing.
	*/
	@Test
	public void whenEngineerPerformTaskThenCorrectDescription() {
		Engineer engineer = new Engineer("Иван Сергеевич", "СПбГУ", 3650, new Task(), "инженерные расчеты");
		Task task = new Task();
		task.setId(12);
		task.setDescription("Задача о нахождении площади треугольника");
		task.setDeadline("05.09.2017");
		Report report = engineer.perform(task);
		String result = report.getDescription();
		String expectString = "Иван Сергеевич решил задачу № 12";
		assertThat(result, is(expectString));
	}
}
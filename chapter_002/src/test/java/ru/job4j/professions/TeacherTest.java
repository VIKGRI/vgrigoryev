package ru.job4j.professions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Class for class Teacher testing.
*@author vgrigoryev
*@since 03.09.2017
*@version 1
*/
public class TeacherTest {
	/**
	*method for remove method testing.
	*/
	@Test
	public void whenTeacherTeachThenCorrectDescription() {
		Knowledge knowledge = new Knowledge();
		knowledge.addSubject("Математика");
		knowledge.addSubject("Физика");
		Teacher teacher = new Teacher("Алексей Петрович", "СПбГУ", 3650, knowledge);
		Student student = new Student();
		student.setName("Иван");
		String result = teacher.teach(student);
		String expectString = "Алексей Петрович учит Иван";
		assertThat(result, is(expectString));
		}
}
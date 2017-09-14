package ru.job4j.colltest;

import java.util.Collection;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Iterator;

/**
*Class provides measuring time of inserting and deleting in different collections.
*@author vgrigoryev
*@since 14.09.2017
*@version 1
*/
public class InsertDeleteTimeTest {
	/**
	*Method is used for evaluating working time of add method.
	*@param collection Specified collection
	*@param amount Amount of strings to add
	*@return measured time in milliseconds of method running
	*/
	public long add(Collection<String> collection, int amount) {
		String randomString;
		long start = System.currentTimeMillis();
		for (int i = 0; i < amount; i++) {
			randomString = RanomStringArrayCreator.createRandomString();
			collection.add(randomString);
		}
		long finish = System.currentTimeMillis();
		return finish - start;
	}
	/**
	*Method is used for evaluating working time of delete method.
	*@param collection Specified collection
	*@param amount Amount of strings to add
	*@return measured time in milliseconds of method running
	*/
	public long delete(Collection<String> collection, int amount) {
		long start = System.currentTimeMillis();
		Iterator<String> it = collection.iterator();
		for (int i = 0; i < amount && it.hasNext(); i++) {
			it.next();
			it.remove();
		}
		long finish = System.currentTimeMillis();
		return finish - start;
	}
	/**
	*Main method.
	*@param args args
	*/
	public static void main(String[] args) {
		InsertDeleteTimeTest test = new InsertDeleteTimeTest();
		ArrayList<String> arrayList = new ArrayList<>();
		LinkedList<String> linkedList = new LinkedList<>();
		TreeSet<String> treeSet = new TreeSet<>();
		int numberOfStringsToAdd = 1000000;
		System.out.println("\n");
		System.out.println("Adding strings in collections: \n");
		System.out.println("Number of strings added: " + numberOfStringsToAdd + "\n");
		System.out.println("Time for ArrayList: " + test.add(arrayList, numberOfStringsToAdd));
		System.out.println("Time for LinkedList: " + test.add(linkedList, numberOfStringsToAdd));
		System.out.println("Time for TreeSet: " + test.add(treeSet, numberOfStringsToAdd));
		System.out.println("Deleting strings from collections: \n");
		int numberOfStringsToDelete = 20000;
		System.out.println("Number of strings deleted: " + numberOfStringsToDelete + "\n");
		System.out.println("Time for ArrayList: " + test.delete(arrayList, numberOfStringsToDelete));
		System.out.println("Time for LinkedList: " + test.delete(linkedList, numberOfStringsToDelete));
		System.out.println("Time for TreeSet: " + test.delete(treeSet, numberOfStringsToDelete));
	}
}

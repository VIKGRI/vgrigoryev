package ru.job4j.bank;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
*Class for Board class testing.
*@author vgrigoryev
*@since 12.09.2017
*@version 1
*/
public class BankTest {
	/**
	 * Method for testing add user in the bank operation.
	 * @throws UnavailableUserDataException if the user's data is not correct
	 */
	@Test
	public void whenAddUserThenPassportMatches() throws UnavailableUserDataException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Bank bank = new Bank();
		User user1 = new User("Sergey Ivanov", 40052639);
		bank.addUser(user1);
		List<User> users = bank.getAllUsers();
		System.out.println(users.get(0).getPassport());
		assertThat(out.toString(), is(String.format("40052639" + "%s",
				System.getProperty("line.separator"))));
	}
	/**
	 * Method for testing delete user from the bank operation.
	 * @throws UnavailableUserDataException if the user's data is not correct
	 */
	@Test
	public void whenAddTwoUserAndDeleteOneThenOneRemains() throws UnavailableUserDataException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Bank bank = new Bank();
		User user1 = new User("Sergey Ivanov", 40052639);
		User user2 = new User("Ivan Petrov", 40214630);
		bank.addUser(user1);
		bank.addUser(user2);
		bank.deleteUser(user2);
		List<User> users = bank.getAllUsers();
		System.out.println(users.get(0).getPassport());
		assertThat(out.toString(), is(String.format("40052639" + "%s",
				System.getProperty("line.separator"))));
	}

	/**
	 *Method for testing addition account to user.
	 * @throws UnavailableUserDataException if the user's data is not correct
	 * @throws UnavailableAccountDataException if data of account are incorrect
	 * @throws NoSuchUserException if such user is not found
	 * @throws RewriteExistingAccountException if such account has already been created
	 */
	@Test
	public void whenAddAccountToUserThenAccountsMatch()
			throws UnavailableUserDataException, UnavailableAccountDataException,
			NoSuchUserException, RewriteExistingAccountException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Bank bank = new Bank();
		User user1 = new User("Sergey Ivanov", 40052639);
		bank.addUser(user1);
		Account account1 = new Account(new Requisites(125), 5500);
		bank.addAccountToUser(user1, account1);
		List<Account> accounts = bank.getUserAccounts(user1);
		System.out.println(accounts.get(0).getAccountNum());
		assertThat(out.toString(), is(String.format("125" + "%s",
				System.getProperty("line.separator"))));
	}
	/**
	 * Method for testing deletetion account from user.
	 * @throws UnavailableUserDataException if the user's data is not correct
	 * @throws UnavailableAccountDataException if data of account are incorrect
	 * @throws NoSuchUserException if such user is not found
	 * @throws RewriteExistingAccountException if such account has already been created
	 * @throws NoSuchAccountException if such account is not found
	 */
	@Test
	public void whenDeleteAccountFromUserThenAccountsMatch()
			throws UnavailableUserDataException, UnavailableAccountDataException,
			NoSuchUserException, RewriteExistingAccountException, NoSuchAccountException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Bank bank = new Bank();
		User user1 = new User("Sergey Ivanov", 40052639);
		bank.addUser(user1);
		Account account1 = new Account(new Requisites(125), 5500);
		Account account2 = new Account(new Requisites(126), 3500);
		Account account3 = new Account(new Requisites(127), 500);
		bank.addAccountToUser(user1, account1);
		bank.addAccountToUser(user1, account2);
		bank.addAccountToUser(user1, account3);
		bank.deleteAccountFromUser(user1, account2);
		List<Account> accounts = bank.getUserAccounts(user1);
		System.out.println(accounts.get(0).getAccountNum() + " "
				+ accounts.get(1).getAccountNum());
		assertThat(out.toString(), is(String.format("125" + " " + "127" + "%s",
				System.getProperty("line.separator"))));
	}
	/**
	 * Method for testing getting all user's accounts.
	 * @throws UnavailableUserDataException if the user's data is not correct
	 * @throws UnavailableAccountDataException if data of account are incorrect
	 * @throws NoSuchUserException if such user is not found
	 * @throws RewriteExistingAccountException if such account has already been created
	 * @throws NoSuchAccountException if such account is not found
	 */
	@Test
	public void whenGetUserAccountsThenAccountsMatch()
			throws UnavailableUserDataException, UnavailableAccountDataException,
			NoSuchUserException, RewriteExistingAccountException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Bank bank = new Bank();
		User user1 = new User("Sergey Ivanov", 40052639);
		bank.addUser(user1);
		Account account1 = new Account(new Requisites(125), 5500);
		Account account2 = new Account(new Requisites(126), 3500);
		bank.addAccountToUser(user1, account1);
		bank.addAccountToUser(user1, account2);
		List<Account> accounts = bank.getUserAccounts(user1);
		System.out.println(accounts.size() + " " + accounts.get(0).getAccountNum() + " "
				+ accounts.get(1).getAccountNum());
		assertThat(out.toString(), is(String.format("2" + " " + "125" + " " + "126" + "%s",
				System.getProperty("line.separator"))));
	}
	/**
	 * Method for testing transferring money from one user to another or to themselves.
	 * @throws UnavailableUserDataException if the user's data is not correct
	 * @throws UnavailableAccountDataException if data of account are incorrect
	 * @throws NoSuchUserException if such user is not found
	 * @throws RewriteExistingAccountException if such account has already been created
	 * @throws NoSuchAccountException if such account is not found
	 */
	@Test
	public void whenTransferMoneyFromUserHasMoneyThenTrue()
			throws UnavailableUserDataException, UnavailableAccountDataException,
			NoSuchUserException, RewriteExistingAccountException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Bank bank = new Bank();
		User user1 = new User("Sergey Ivanov", 40052639);
		User user2 = new User("Alex Petrov", 40052512);
		bank.addUser(user1);
		bank.addUser(user2);
		Account account1 = new Account(new Requisites(125), 5500);
		Account account2 = new Account(new Requisites(126), 3500);
		bank.addAccountToUser(user1, account1);
		bank.addAccountToUser(user2, account2);
		boolean expect = bank.transferMoney(user1, account1, user2, account2, 2500);
		List<Account> accounts1 = bank.getUserAccounts(user1);
		List<Account> accounts2 = bank.getUserAccounts(user2);
		System.out.println(expect + " " + accounts1.get(0).getValue() + " "
				+ accounts2.get(0).getValue());
		assertThat(out.toString(), is(String.format("true " + "3000.0" + " " + "6000.0" + "%s",
				System.getProperty("line.separator"))));
	}
	/**
	 * Method for testing transferring money from one user to another or to themselves.
	 * @throws UnavailableUserDataException if the user's data is not correct
	 * @throws UnavailableAccountDataException if data of account are incorrect
	 * @throws NoSuchUserException if such user is not found
	 * @throws RewriteExistingAccountException if such account has already been created
	 * @throws NoSuchAccountException if such account is not found
	 */
	@Test
	public void whenTransferMoneyFromUserHasNoMoneyThenFalse()
			throws UnavailableUserDataException, UnavailableAccountDataException,
			NoSuchUserException, RewriteExistingAccountException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Bank bank = new Bank();
		User user1 = new User("Sergey Ivanov", 40052639);
		User user2 = new User("Alex Petrov", 40052512);
		bank.addUser(user1);
		bank.addUser(user2);
		Account account1 = new Account(new Requisites(125), 5500);
		Account account2 = new Account(new Requisites(126), 3500);
		bank.addAccountToUser(user1, account1);
		bank.addAccountToUser(user2, account2);
		boolean expect = bank.transferMoney(user1, account1, user2, account2, 10000);
		List<Account> accounts1 = bank.getUserAccounts(user1);
		List<Account> accounts2 = bank.getUserAccounts(user2);
		System.out.println(expect + " " + accounts1.get(0).getValue() + " "
				+ accounts2.get(0).getValue());
		assertThat(out.toString(), is(String.format("false " + "5500.0" + " " + "3500.0" + "%s",
				System.getProperty("line.separator"))));
	}
	/**
	 * Method for testing transferring money from one user to another or to themselves.
	 * @throws UnavailableUserDataException if the user's data is not correct
	 * @throws UnavailableAccountDataException if data of account are incorrect
	 * @throws NoSuchUserException if such user is not found
	 * @throws RewriteExistingAccountException if such account has already been created
	 * @throws NoSuchAccountException if such account is not found
	 */
	@Test
	public void whenGetAllUsersThenListsMatch()
			throws UnavailableUserDataException, UnavailableAccountDataException,
			NoSuchUserException, RewriteExistingAccountException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		Bank bank = new Bank();
		User user1 = new User("Sergey Ivanov", 40052639);
		User user2 = new User("Alex Petrov", 40052512);
		User user3 = new User("John Smith", 40053258);
		bank.addUser(user1);
		bank.addUser(user2);
		bank.addUser(user3);
		Account account1 = new Account(new Requisites(125), 5500);
		Account account2 = new Account(new Requisites(126), 3500);
		Account account3 = new Account(new Requisites(125), 3800);
		bank.addAccountToUser(user1, account1);
		bank.addAccountToUser(user2, account2);
		bank.addAccountToUser(user3, account3);
		Set<User> users = new HashSet<User>(bank.getAllUsers());
		Set<User> expect = new HashSet<User>();
		expect.add(user1);
		expect.add(user2);
		expect.add(user3);
		assertThat(users, is(expect));
	}
}
package net.scilingo.se452.banking;

import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.Query;

import org.junit.Before;

import net.scilingo.se452.banking.Account;
import net.scilingo.se452.banking.AccountType;
import net.scilingo.se452.banking.Customer;

public class CustomerTest extends DatabaseTest {
	
	@Test
	public void testCustomerSearch1() {
		
		Customer janeEJones = getEm().find(Customer.class, 1);
		assertEquals("Jane", janeEJones.getFirstName());
		assertEquals("E", janeEJones.getMiddleInitial());
		assertEquals("Jones", janeEJones.getLastName());
		assertEquals("5701 S. Madison", janeEJones.getAddress().getAddressLine1());
		assertEquals("Hinsdale", janeEJones.getAddressInfo().getCity());
		assertEquals("Dupage", janeEJones.getAddressInfo().getCounty());
		assertEquals("Illinois", janeEJones.getAddressInfo().getState());
		assertEquals(60521, janeEJones.getAddressInfo().getZipcode());
	}
	
	@Test
	public void testCustomerSearch2() {
	
		Customer shannonJMiller = getBankingService().getCustomer("Shannon", "J", "Miller");
		
		assertEquals("Shannon",shannonJMiller.getFirstName());
		assertEquals("J", shannonJMiller.getMiddleInitial());
		assertEquals("Miller", shannonJMiller.getLastName());	
		assertEquals("Chicago", shannonJMiller.getAddressInfo().getCity());
		assertEquals("Illinois", shannonJMiller.getAddressInfo().getState());
	}
	
	/**
	 * Test to Update Savings Account for Shannon J Miller
	 * Balance for Savings Account will become 200000
	 * From previous 100000
	 */
	@Test 
	public void testUpdateAccount1() {
		
		Customer shannonMiller = getBankingService().getCustomer("Shannon", "J", "Miller");
		Account savingsAccount = null;
		
		for(Account a : shannonMiller.getAccounts()) {
			if(a.getAccountType().equals(AccountType.SAVINGS)) {
				savingsAccount = a;
				savingsAccount.setBalance(200000);
				getBankingService().updateAccount(savingsAccount);
			}
		}
		
		assertEquals(200000, savingsAccount.getBalance());
		
	}
	
	/**
	 * Test to Update Checking Account for Jane E Jones
	 * Checking Account will be persisted with balance increased by 10 
	 * 
	 */
	@Test
	public void testUpdateAccount2() {
		
		Customer janeEJones = getBankingService().getCustomer("Jane", "E", "Jones");
		Account checkingAccount = null;
		int previousBalance = 0;
		
		for(Account a: janeEJones.getAccounts()) {
			if(a.getAccountType().equals(AccountType.CHECKING)) {
				checkingAccount = a;
				previousBalance = checkingAccount.getBalance();
				checkingAccount.setBalance(checkingAccount.getBalance() + 10);
				getBankingService().updateAccount(checkingAccount);
			}
		}
		
		assertEquals(previousBalance + 10, checkingAccount.getBalance());
		
	}
	
	@Test 
	public void testUpdateCustomerName1() {
		
		Customer toddKSchmit = getBankingService().getCustomer("Todd", "K", "Schmit");

		toddKSchmit.setFirstName("Ted");
		toddKSchmit.setMiddleInitial("D");
		toddKSchmit.setLastName("Behr");
		getBankingService().updateCustomer(toddKSchmit);
		
		assertEquals(toddKSchmit.getFirstName(), "Ted");
		assertEquals(toddKSchmit.getMiddleInitial(), "D");
		assertEquals(toddKSchmit.getLastName(), "Behr");
	}

}

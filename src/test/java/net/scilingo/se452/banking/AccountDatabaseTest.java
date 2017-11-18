package net.scilingo.se452.banking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AccountDatabaseTest extends DatabaseTest {

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
				savingsAccount.setBalance(200000.00);
				getBankingService().updateAccount(savingsAccount);
			}
		}
		
		assertEquals(Double.valueOf(200000.00), savingsAccount.getBalance());
		
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
		double previousBalance = 0.00;
		
		for(Account a: janeEJones.getAccounts()) {
			if(a.getAccountType().equals(AccountType.CHECKING)) {
				checkingAccount = a;
				previousBalance = checkingAccount.getBalance();
				checkingAccount.setBalance(checkingAccount.getBalance() + 10);
				getBankingService().updateAccount(checkingAccount);
			}
		}
		
		assertEquals(Double.valueOf(previousBalance + 10), checkingAccount.getBalance());	
	}
	
}

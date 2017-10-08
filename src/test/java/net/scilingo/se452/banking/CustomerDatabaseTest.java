package net.scilingo.se452.banking;

import org.junit.Test;
import static org.junit.Assert.*;


import net.scilingo.se452.banking.Account;
import net.scilingo.se452.banking.AccountType;
import net.scilingo.se452.banking.Customer;

public class CustomerDatabaseTest extends DatabaseTest {
	
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
		assertEquals("Cook", shannonJMiller.getAddressInfo().getCounty());
		assertEquals(60605, shannonJMiller.getAddressInfo().getZipcode());
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
		
		// Revert Name Back 
		
		toddKSchmit.setFirstName("Todd");
		toddKSchmit.setMiddleInitial("K");
		toddKSchmit.setLastName("Schmit");
		getBankingService().updateCustomer(toddKSchmit);
		
		assertEquals(toddKSchmit.getFirstName(), "Todd");
		assertEquals(toddKSchmit.getMiddleInitial(), "K");
		assertEquals(toddKSchmit.getLastName(), "Schmit");
		
	}

}

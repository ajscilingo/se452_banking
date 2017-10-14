package net.scilingo.se452.banking;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;



@RunWith(PowerMockRunner.class)
@PrepareForTest ({ CustomerService.class })
public class CustomerServiceTest extends Stubtest{
	
	private static final String queryString = "Select c from Customer c where c.firstName = :firstName and c.middleInitial = :middleInitial and c.lastName = :lastName";
	private CustomerService customerService;
	
	@Mock 
	private Query query;
	
	@Before
	public void setUp() {
		customerService = new CustomerService();
		MockitoAnnotations.initMocks(this);
		customerService.setEntityManager(entityManager);
	}
	
	@After
	public void tearDown() {
		//customerService = null;
	}
	
	@Test
	public void FindCustomer1(){
		
		Address mockAddress = buildMockAddress("4740 N. County Line Road", "", "Cook", 60521);
		Customer mockJohnHSmith = buildMockCustomer("John", "H", "Smith", mockAddress, "Hinsdale", "Illinois");
		when(entityManager.createQuery(queryString)).thenReturn(query);
		when(customerService.getCustomer("John", "H", "Smith")).thenReturn(mockJohnHSmith);
		Customer customer = customerService.getCustomer("John", "H", "Smith");
		verify(entityManager, times(2)).createQuery(queryString);
		assertEquals(customer, mockJohnHSmith);
		
	}
	
	@Test
	public void FindCustomer2() {
		Address mockAddress = buildMockAddress("1529 S. State Street", "APT 6-C", "Cook", 60605);
		Customer mockAnthonyJScilingo =  buildMockCustomer("Anthony", "J", "Scilingo", mockAddress, "Chicago", "Illinois");
		when(entityManager.find(Customer.class, mockAnthonyJScilingo.getId())).thenReturn(mockAnthonyJScilingo);
		Customer customer = customerService.getCustomer(mockAnthonyJScilingo);
		verify(entityManager, times(1)).find(Customer.class, mockAnthonyJScilingo.getId());
		assertEquals(customer, mockAnthonyJScilingo);
		
	}
}

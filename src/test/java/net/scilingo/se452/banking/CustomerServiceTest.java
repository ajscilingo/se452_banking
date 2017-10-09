package net.scilingo.se452.banking;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.times;

import javax.persistence.Query;
import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.mockito.invocation.InvocationOnMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.scilingo.se452.banking.interfaces.IAddress;


@RunWith(PowerMockRunner.class)
@PrepareForTest ({ CustomerService.class })
public class CustomerServiceTest {
	
	private CustomerService customerService;
	
	private final String queryString1 = "Select c from Customer c where c.firstName = :firstName and c.middleInitial = :middleInitial and c.lastName = :lastName";
	
	@Mock
	private EntityManager entityManager;
	
	@Mock 
	private Query query;
	
	@Before
	public void setUp() {
		customerService = new CustomerService();
		MockitoAnnotations.initMocks(this);
		customerService.setEntityManager(entityManager);
	}
	
	@Test
	public void FindCustomer1(){
		
		Address mockAddress = buildMockAddress("4740 N. County Line Road", "", "Cook", 60521);
		Customer mockJohnHSmith = buildMockCustomer("John", "H", "Smith", mockAddress);
		when(entityManager.createQuery(queryString1)).thenReturn(query);
		when(customerService.getCustomer("John", "H", "Smith")).thenReturn(mockJohnHSmith);
		verify(entityManager, times(1)).createQuery(queryString1);
		assertEquals("John", mockJohnHSmith.getFirstName());
		assertEquals("H", mockJohnHSmith.getMiddleInitial());
		assertEquals("Smith", mockJohnHSmith.getLastName());
		assertNotEquals(0, mockJohnHSmith.getId());
	}
	
	private Customer buildMockCustomer(String firstName, String middleInitial, String lastName, IAddress address){
		
		Customer mockedCustomer = new Customer();
		mockedCustomer.setFirstName(firstName);
		mockedCustomer.setLastName(lastName);
		mockedCustomer.setMiddleInitial(middleInitial);
		mockedCustomer.setAddress(address);
		
		return mockedCustomer;
	}
	
	private Address buildMockAddress(String addressLine1, String addressLine2, String county, int zipcode) {
		Address mockedAddress = new Address();
		mockedAddress.setAddressLine1(addressLine1);
		mockedAddress.setAddressLine2(addressLine2);
		mockedAddress.setCounty(county);
		mockedAddress.setZipcode(zipcode);
		
		return mockedAddress;
	}
}

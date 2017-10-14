package net.scilingo.se452.banking;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

import javax.persistence.Query;
import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

import com.mysql.jdbc.StringUtils;

import net.scilingo.se452.banking.interfaces.IAddress;


@RunWith(PowerMockRunner.class)
@PrepareForTest ({ CustomerService.class })
public class CustomerServiceTest {
	
	private CustomerService customerService;
	
	private static final String queryString = "Select c from Customer c where c.firstName = :firstName and c.middleInitial = :middleInitial and c.lastName = :lastName";
	private static Random randomId;
	private static final int maxId = 100, minId = 1;
	
	@Mock
	private EntityManager entityManager;
	
	@Mock 
	private Query query;
	
	@BeforeClass
	public static void setUpClass() {
		randomId = new Random();
	}
	
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
	public void FindCustomer1_HappyPath(){
		
		Address mockAddress = buildMockAddress("4740 N. County Line Road", "", "Cook", 60521);
		Customer mockJohnHSmith = buildMockCustomer("John", "H", "Smith", mockAddress, "Hinsdale", "Illinois");
		when(entityManager.createQuery(queryString)).thenReturn(query);
		when(customerService.getCustomer("John", "H", "Smith")).thenReturn(mockJohnHSmith);
		verify(entityManager, times(1)).createQuery(queryString);
		assertEquals("John", mockJohnHSmith.getFirstName());
		assertEquals("H", mockJohnHSmith.getMiddleInitial());
		assertEquals("Smith", mockJohnHSmith.getLastName());
		assertEquals("Hinsdale", mockJohnHSmith.getAddressInfo().getCity());
		assertEquals("Illinois", mockJohnHSmith.getAddressInfo().getState());
		assertEquals("Cook", mockJohnHSmith.getAddressInfo().getCounty());
		assertEquals(60521, mockJohnHSmith.getAddressInfo().getZipcode());
		assertNotEquals(0, mockJohnHSmith.getId());
		
	}
	
	@Test
	public void FindCustomer2() {
		Address mockAddress = buildMockAddress("1529 S. State Street", "APT 6-C", "Cook", 60605);
		Customer mockAnthonyJScilingo =  buildMockCustomer("Anthony", "J", "Scilingo", mockAddress, "Chicago", "Illinois");
		Customer mockAnthonyJScilingo2 = new Customer();
		mockAnthonyJScilingo2.setId(mockAnthonyJScilingo.getId());
		mockAnthonyJScilingo2.setFirstName(mockAnthonyJScilingo.getFirstName());
		mockAnthonyJScilingo2.setMiddleInitial(mockAnthonyJScilingo.getMiddleInitial());
		mockAnthonyJScilingo2.setLastName(mockAnthonyJScilingo.getLastName());
		mockAnthonyJScilingo2.setAddress(mockAddress);
		mockAnthonyJScilingo2.setAddressInfo(buildMockAddressInfo("Chicago", "Illinois", mockAddress.getCounty(), mockAddress.getZipcode()));
		when(entityManager.find(Customer.class, mockAnthonyJScilingo.getId())).thenReturn(mockAnthonyJScilingo2);
		when(customerService.getCustomer(mockAnthonyJScilingo)).thenReturn(mockAnthonyJScilingo2);
		
	}
	
	@Test
	public void FindCustomer3_IdIsZero() {
		Address mockAddress = buildMockAddress("1700 S. State Street", "APT 801", "Cook", 60605);
		Customer mockPerson =  new Customer();
		
		CustomerService spyCustServ = spy(customerService);
		
		mockPerson.setFirstName("Jenny");
		mockPerson.setMiddleInitial("E");
		mockPerson.setLastName("Jones");
		mockPerson.setAddress(mockAddress);
		when(spyCustServ.getCustomer(mockPerson)).thenReturn(null);
		assertEquals(0, mockPerson.getId());
	}
	
	private Customer buildMockCustomer(String firstName, String middleInitial, String lastName, IAddress address, String city, String state){
		
		Customer mockedCustomer = new Customer();
		mockedCustomer.setFirstName(firstName);
		mockedCustomer.setLastName(lastName);
		mockedCustomer.setMiddleInitial(middleInitial);
		mockedCustomer.setAddress(address);
		mockedCustomer.setAddressInfo(buildMockAddressInfo(city, state, address.getCounty(), address.getZipcode()));
		mockedCustomer.setId(randomId.nextInt((maxId - minId) + 1) + minId);
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
	
	private AddressInfo buildMockAddressInfo(String city, String state, String county, int zipcode) {
		AddressInfo mockedAddressInfo = spy(new AddressInfo());
		doReturn(zipcode).when(mockedAddressInfo).getZipcode();
		doReturn(county).when(mockedAddressInfo).getCounty();
		doReturn(city).when(mockedAddressInfo).getCity();
		doReturn(state).when(mockedAddressInfo).getState();
		return mockedAddressInfo;
	}
}

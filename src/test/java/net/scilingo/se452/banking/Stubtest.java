package net.scilingo.se452.banking;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.BeforeClass;
import org.mockito.Mock;

import net.scilingo.se452.banking.interfaces.IAddress;

public class Stubtest {

	protected static Random randomId;
	protected static final int maxId = 100, minId = 1;
	
	@Mock
	protected EntityManager entityManager;
	
	@BeforeClass
	public static void setUpClass() {
		randomId = new Random();
		
	}
	
	protected Customer buildMockCustomer(String firstName, String middleInitial, String lastName, IAddress address, String city, String state){
		
		Customer mockedCustomer = new Customer();
		mockedCustomer.setFirstName(firstName);
		mockedCustomer.setLastName(lastName);
		mockedCustomer.setMiddleInitial(middleInitial);
		mockedCustomer.setAddress(address);
		mockedCustomer.setAddressInfo(buildMockAddressInfo(city, state, address.getCounty(), address.getZipcode()));
		mockedCustomer.setId(randomId.nextInt((maxId - minId) + 1) + minId);
		return mockedCustomer;
	}
	
	protected Address buildMockAddress(String addressLine1, String addressLine2, String county, int zipcode) {
		Address mockedAddress = new Address();
		mockedAddress.setAddressLine1(addressLine1);
		mockedAddress.setAddressLine2(addressLine2);
		mockedAddress.setCounty(county);
		mockedAddress.setZipcode(zipcode);
		
		return mockedAddress;
	}
	
	protected AddressInfo buildMockAddressInfo(String city, String state, String county, int zipcode) {
		AddressInfo mockedAddressInfo = spy(new AddressInfo());
		doReturn(zipcode).when(mockedAddressInfo).getZipcode();
		doReturn(county).when(mockedAddressInfo).getCounty();
		doReturn(city).when(mockedAddressInfo).getCity();
		doReturn(state).when(mockedAddressInfo).getState();
		return mockedAddressInfo;
	}
	
	protected Account buildMockAccount(Integer balance, AccountType accountType) {
		Account mockedAccount = new Account();
		mockedAccount.setAccountType(accountType);
		mockedAccount.setBalance(balance);
		mockedAccount.setId(randomId.nextInt((maxId - minId) + 1) + minId);
		return mockedAccount;
	}
	
	protected Customer buildHinsdaleDupageCustomer() {

		Customer mockedCustomer = new Customer();
		mockedCustomer.setFirstName("Tom");
		mockedCustomer.setLastName("Jones");
		mockedCustomer.setMiddleInitial("L");
		mockedCustomer.setAddress(buildMockAddress("4920 N. Elm", "", "Dupage", 60521));
		mockedCustomer.setId(randomId.nextInt((maxId - minId) + 1) + minId);
		return mockedCustomer;
	}
	
	protected Account buildCheckingAccountWithTwentyThousandDollars() {
		return buildMockAccount(20000, AccountType.CHECKING);
	}
	
	protected Account buildAccountWithNoId() {
		Account mockedAccount = new Account();
		mockedAccount.setAccountType(AccountType.SAVINGS);
		mockedAccount.setBalance(5000);
		return mockedAccount;
	}
	
	protected List<Account> buildMockListOfAccounts(){
		List<Account> mockedAccounts = new ArrayList<Account>();
		mockedAccounts.add(buildMockAccount(400, AccountType.CHECKING));
		mockedAccounts.add(buildMockAccount(500, AccountType.CD));
		mockedAccounts.add(buildMockAccount(800, AccountType.RESERVED));
		mockedAccounts.add(buildMockAccount(9000, AccountType.SAVINGS));
		return mockedAccounts;
	}
}

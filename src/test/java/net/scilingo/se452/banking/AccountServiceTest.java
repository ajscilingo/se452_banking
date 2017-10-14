package net.scilingo.se452.banking;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {AccountService.class})
public class AccountServiceTest extends Stubtest {

	private AccountService accountService;
	
	@Mock
	EntityTransaction entityTransaction;
	
	@Mock 
	private Query query;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountService(entityManager);
	}
	
	@Test 
	public void testCreateNewAccount1() {
		Customer mockedCustomer = buildHinsdaleDupageCustomer();
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		accountService.createNewAccount(mockedCustomer, 20000, AccountType.CHECKING);
		Account mockedAccount = mockedCustomer.getAccounts().iterator().next();
		verify(entityManager, times(1)).persist(mockedAccount);
		assertEquals(Integer.valueOf(20000), mockedAccount.getBalance());
		assertEquals(AccountType.CHECKING, mockedAccount.getAccountType());
		assertEquals(mockedCustomer, mockedAccount.getCustomer());
	}
	/**
	 * Account deleted
	 */
	@Test 
	public void testDeleteAccount1() {
		
		Customer mockedCustomer = buildHinsdaleDupageCustomer();
		mockedCustomer.getAccounts().add(buildCheckingAccountWithTwentyThousandDollars());
		Account mockedAccount = mockedCustomer.getAccounts().iterator().next();
		mockedAccount.setCustomer(mockedCustomer);
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		when(entityManager.find(Account.class, mockedAccount.getId())).thenReturn(mockedAccount);
		accountService.deleteAccount(mockedAccount);
		verify(entityManager, times(1)).find(Account.class, mockedAccount.getId());
		verify(entityManager, times(1)).getTransaction();
		verify(entityTransaction, times(1)).begin();
		verify(entityManager, times(1)).remove(mockedAccount);
		verify(entityTransaction, times(1)).commit();
	}
	
	/**
	 * Account has no id so nothing is deleted
	 * Should never happen
	 */
	@Test 
	public void testDeleteAccount2() {
		
		Customer mockedCustomer = buildHinsdaleDupageCustomer();
		mockedCustomer.getAccounts().add(buildAccountWithNoId());
		Account mockedAccount = mockedCustomer.getAccounts().iterator().next();
		mockedAccount.setCustomer(mockedCustomer);
		accountService.deleteAccount(mockedAccount);
		assertNull(mockedAccount.getId());
	}
	
	
	@Test
	public void testSaveAccount1() {
		
		Customer mockedCustomer = buildHinsdaleDupageCustomer();
		mockedCustomer.getAccounts().add(buildCheckingAccountWithTwentyThousandDollars());
		Account mockedAccount = mockedCustomer.getAccounts().iterator().next();
		mockedAccount.setCustomer(mockedCustomer);
		when(entityManager.getTransaction()).thenReturn(entityTransaction);
		when(entityManager.find(Account.class, mockedAccount.getId())).thenReturn(mockedAccount);
		assertEquals(Integer.valueOf(20000),mockedAccount.getBalance());
		mockedAccount.setBalance(mockedAccount.getBalance() + 10000);
		accountService.saveAccount(mockedAccount);
		verify(entityManager, times(1)).find(Account.class, mockedAccount.getId());
		verify(entityManager, times(1)).getTransaction();
		verify(entityTransaction, times(1)).begin();
		verify(entityManager, times(1)).persist(mockedAccount);
		verify(entityTransaction, times(1)).commit();
	}
	
	@Test
	public void testGetAllAccounts() {
		List<Account> mockedAccounts = buildMockListOfAccounts();
		when(entityManager.createNamedQuery("getAllAccounts")).thenReturn(query);
		when(accountService.getAllAccounts()).thenReturn(mockedAccounts);
		List<Account> response = accountService.getAllAccounts();
		verify(entityManager, times(2)).createNamedQuery("getAllAccounts");
		assertNotNull(response);
		assertEquals(4, response.size());
	}
	
}

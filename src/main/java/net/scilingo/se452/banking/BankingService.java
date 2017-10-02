package net.scilingo.se452.banking;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;

public class BankingService {
	
	private final EntityManager _entityManager;
	private AccountService _accountService;
	private CustomerService _customerService;
	private MongoCustomerService _mongoCustomerService;
	private final MongoClient _mongoClient;
	
	public BankingService() {
		this(Persistence.createEntityManagerFactory("bankXYZ_PU").createEntityManager(), new MongoClient());
	}
	
	public BankingService(EntityManager entityManager, MongoClient mongoClient) {
		this._entityManager = entityManager;
		this._mongoClient = mongoClient;
		this._accountService = new AccountService(_entityManager);
		this._customerService = new CustomerService(_entityManager);
		this._mongoCustomerService = new MongoCustomerService(_mongoClient);
	}
	
	public void createNewCustomer(String firstName, String middleInitial, String lastName, Address address) {
		this._customerService.createCustomer(firstName, middleInitial, lastName, address);
		this._mongoCustomerService.createCustomer(firstName, middleInitial, lastName, address);
	}
	
	public List<Customer> getAllCustomers(){
		return this._customerService.getAllCustomers();
	}
	
	public List<Customer> getCustomers(String firstName, String middleInitial, String lastName){
		return this._customerService.getCustomers(firstName, middleInitial, lastName);
	}
	
	public void updateCustomer(Customer customer) {
		this._customerService.saveCustomer(customer);
	}
	
	public Customer getCustomer(String firstName, String middleInitial, String lastName){
		return this._customerService.getCustomer(firstName, middleInitial, lastName);
	}
	
	public void deleteCustomer(Customer customer) {
		this._customerService.deleteCustomer(customer);
		this._mongoCustomerService.deleteCustomer(customer);
	}
	
	public void createNewCheckingAccount(Customer customer, int balance) {
		this._accountService.createNewAccount(customer, balance, AccountType.CHECKING);
	}
	
	public void createNewSavingsAccount(Customer customer, int balance) {
		this._accountService.createNewAccount(customer, balance, AccountType.SAVINGS);
	}
	
	public void createNewCDAccount(Customer customer, int balance) {
		this._accountService.createNewAccount(customer, balance, AccountType.CD);
	}
	
	public void updateAccount(Account account) {
		this._accountService.saveAccount(account);
	}
	
}

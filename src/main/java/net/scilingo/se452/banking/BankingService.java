package net.scilingo.se452.banking;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Service;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import net.scilingo.se452.banking.interfaces.IAddress;
import net.scilingo.se452.banking.interfaces.IAddressInfo;
import net.scilingo.se452.banking.interfaces.ICustomer;
import net.scilingo.se452.banking.mongodb.MongoAddress;
import net.scilingo.se452.banking.mongodb.MongoCustomerService;
import net.scilingo.se452.banking.mongodb.MongoPropertiesLoader;

@Service
public class BankingService {
	
	private final EntityManager _entityManager;
	private AccountService _accountService;
	private CustomerService _customerService;
	private AddressInfoService _addressInfoService;
	private MongoCustomerService _mongoCustomerService;
	private UserCustomerService _userCustomerService;
	private final MongoClient _mongoClient;
	
	
	public BankingService() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bankXYZ_PU");
		EntityManager entityManager = emf.createEntityManager();
		
		Properties mongoProperties = null;
		
		try {
			mongoProperties = MongoPropertiesLoader.getMongoProperties();
		}
		catch(IOException e) {
			System.out.println("Can't find mongoclient.properties");
			e.printStackTrace();
		}
		
		
		StringBuilder mongoURIBuilder = new StringBuilder();
		mongoURIBuilder
			.append("mongodb://")
			.append(mongoProperties.get("USER"))
			.append(":")
			.append(mongoProperties.getProperty("PASSWORD"))
			.append("@")
			.append(mongoProperties.getProperty("HOST"))
			.append(":")
			.append(mongoProperties.getProperty("PORT"))
			.append("/")
			.append(mongoProperties.getProperty("DATABASE"));
		
		MongoClientURI mcURI = new MongoClientURI(mongoURIBuilder.toString());
		MongoClient mongoClient = new MongoClient(mcURI);
		
		this._entityManager = entityManager;
		this._mongoClient = mongoClient;
		this._accountService = new AccountService(_entityManager);
		this._addressInfoService = new AddressInfoService(_entityManager);
		this._customerService = new CustomerService(_entityManager, _addressInfoService);
		this._mongoCustomerService = new MongoCustomerService(_mongoClient);
		this._userCustomerService = new UserCustomerService(_entityManager);
	}
	
	public BankingService(EntityManager entityManager, MongoClient mongoClient) {
		this._entityManager = entityManager;
		this._mongoClient = mongoClient;
		this._accountService = new AccountService(_entityManager);
		this._addressInfoService = new AddressInfoService(_entityManager);
		this._customerService = new CustomerService(_entityManager, _addressInfoService);
		this._mongoCustomerService = new MongoCustomerService(_mongoClient);
	}
	
	public ICustomer createNewCustomer(String firstName, String middleInitial, String lastName, Address address) {
		Customer newCustomer = (Customer) this._customerService.createCustomer(firstName, middleInitial, lastName, address);
		MongoAddress mongoAddress = createMongoAddress(newCustomer.getAddress(), newCustomer.getAddressInfo());
		this._mongoCustomerService.createCustomer(firstName, middleInitial, lastName, mongoAddress);
		return newCustomer;
	}
	
	public ICustomer createNewCustomer(Customer customer) {
		Customer newCustomer = (Customer) this._customerService.createCustomer(customer);
		return newCustomer;
	}
	
	public UserCustomer createNewUserCustomer(UserCustomer userCustomer) {
		UserCustomer newUserCustomer = this._userCustomerService.createUserCustomer(userCustomer);
		return newUserCustomer;
	}
	
	public UserCustomer getUserCustomer(UserCustomer userCustomer) {
		UserCustomer currentUserCustomer = this._userCustomerService.getUserCustomer(userCustomer);
		return currentUserCustomer;
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
	
	public Customer getCustomer(String id) {
		return this._customerService.getCustomer(id);
	}
	
	public Customer getCustomer(Customer customer) {
		return this._customerService.getCustomer(customer);
	}
	
	public Customer getCustomerByUserName(String username) {
		return this._userCustomerService.getCustomerForUsername(username);
	}
	
	public void deleteCustomer(Customer customer) {
		this._customerService.deleteCustomer(customer);
		this._mongoCustomerService.deleteCustomer(customer);
	}
	
	public void createNewCheckingAccount(Customer customer, double balance) {
		this._accountService.createNewAccount(customer, balance, AccountType.CHECKING);
	}
	
	public void createNewSavingsAccount(Customer customer, double balance) {
		this._accountService.createNewAccount(customer, balance, AccountType.SAVINGS);
	}
	
	public void createNewCDAccount(Customer customer, double balance) {
		this._accountService.createNewAccount(customer, balance, AccountType.CD);
	}
	
	public void updateAccount(Account account) {
		this._accountService.saveAccount(account);
	}
	
	public void makeDeposit(Deposit deposit) {
		this._accountService.makeDeposit(deposit);
	}
	
	public void makeWithdraw(Withdraw withdraw) {
		this._accountService.makeWithdraw(withdraw);
	}

	public Account getAccount(Account account) {
		return this._accountService.getAccount(account);
	}
	
	private MongoAddress createMongoAddress(IAddress address, IAddressInfo addressInfo) {
		
		MongoAddress mongoAddress = new MongoAddress();
		
		if(address != null) {
			mongoAddress.setAddressLine1(address.getAddressLine1());
			mongoAddress.setAddressLine2(address.getAddressLine2());
			mongoAddress.setCounty(address.getCounty());
			mongoAddress.setZipcode(address.getZipcode());
		}
		
		if(addressInfo != null) {
			mongoAddress.setCity(addressInfo.getCity());
			mongoAddress.setState(addressInfo.getState());
		}
		
		return mongoAddress;
	}
	
	public List<Integer> getAllZipcodes(){
		return this._addressInfoService.getZipcodes();
	}
	
	public List<String> getAllCounties(){
		return this._addressInfoService.getCounties();
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		_entityManager.close();
	}
}

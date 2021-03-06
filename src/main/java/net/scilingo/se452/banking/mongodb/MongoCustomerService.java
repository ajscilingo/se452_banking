package net.scilingo.se452.banking.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import net.scilingo.se452.banking.interfaces.IAddress;
import net.scilingo.se452.banking.interfaces.ICustomer;
import net.scilingo.se452.banking.interfaces.ICustomerService;

import static com.mongodb.client.model.Filters.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.springframework.stereotype.Service;

public class MongoCustomerService implements ICustomerService{

	private MongoClient _mongoClient;
	private MongoCollection<Document> _db;
	private final String FIRST_NAME = "firstName";
	private final String MIDDLE_INITIAL = "middleInitial";
	private final String LAST_NAME = "lastName";
	private final String ADDRESS = "address";
	private final String ADDRESS_LINE1 = "addressLine1";
	private final String ADDRESS_LINE2 = "addressLine2";
	private final String CITY = "city";
	private final String STATE = "state";
	private final String COUNTY = "county";
	private final String ZIPCODE = "zipcode";
	
	private static final Logger logger = Logger.getLogger(MongoCustomerService.class.getName());
	
	public MongoCustomerService(MongoClient mongoClient) {
		this._mongoClient = mongoClient;
		this._db = _mongoClient.getDatabase("se_452").getCollection("customers");
	}
	
	@Override
	public void saveCustomer(ICustomer customer) {
		
	}

	@Override
	public void deleteCustomer(ICustomer customer) {
		Document customerToDelete =
				_db.findOneAndDelete( and(eq("firstName", customer.getFirstName()), 
						  				 eq("middleInitial", customer.getMiddleInitial()), 
						  				 eq("lastName", customer.getLastName())) );
		
		logger.log(Level.INFO, "Customer {0} {1} {2} removed in MongoDB", new String[]{customer.getFirstName(), customer.getMiddleInitial(), customer.getLastName()});
	}

	@Override
	public ICustomer createCustomer(String firstName, String middleInitial, String lastName, IAddress address) {
		
		MongoCustomer customer = new MongoCustomer();
		customer.setFirstName(firstName);
		customer.setMiddleInitial(middleInitial);
		customer.setLastName(lastName);

		customer.setAddress((MongoAddress)address);
		
		logger.log(Level.INFO, "Creating Customer {0} {1} {2} in MongoDB", new String[]{customer.getFirstName(), customer.getMiddleInitial(), customer.getLastName()});
		
		_db.insertOne(new Document()
				.append(FIRST_NAME, customer.getFirstName())
				.append(MIDDLE_INITIAL, customer.getMiddleInitial())
				.append(LAST_NAME, customer.getLastName())
				.append(ADDRESS, new Document()
						.append(ADDRESS_LINE1, customer.getAddress().getAddressLine1())
						.append(ADDRESS_LINE2, customer.getAddress().getAddressLine2())
						.append(CITY, ((MongoAddress)customer.getAddress()).getCity())
						.append(STATE, ((MongoAddress)customer.getAddress()).getState())
						.append(COUNTY, customer.getAddress().getCounty())
						.append(ZIPCODE, customer.getAddress().getZipcode()))
				);
		
		return customer;
	}

}

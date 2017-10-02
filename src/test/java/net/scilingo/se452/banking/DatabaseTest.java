package net.scilingo.se452.banking;

import java.io.IOException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import net.scilingo.se452.banking.Address;
import net.scilingo.se452.banking.BankingService;
import net.scilingo.se452.banking.Customer;

public class DatabaseTest
{

	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static BankingService bs;
	private static MongoClient mc;
	
	@BeforeClass
	public static void setupClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("bankXYZ_PU");
		em = emf.createEntityManager();
		
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
		mc = new MongoClient(mcURI);
		bs = new BankingService(em, mc);
		
		Address address = new Address();
		address.setAddressLine1("1400 S. State");
		address.setAddressLine2("");
		address.setZipcode(60605);
		address.setCounty("Cook");
		bs.createNewCustomer("Shannon", "J", "Miller", address);
		
		Customer shannonMiller = bs.getCustomer("Shannon", "J", "Miller");
		
		bs.createNewCheckingAccount(shannonMiller, 5000);
		bs.createNewSavingsAccount(shannonMiller, 10000);
		bs.createNewCDAccount(shannonMiller, 15000);
		
		Address address2 = new Address();
		address2.setAddressLine1("6250 S. Madison");
		address2.setAddressLine2("");
		address2.setZipcode(60527);
		address2.setCounty("Dupage");
		bs.createNewCustomer("Todd","K","Schmit", address2);
		
	}
	
	@AfterClass
	public static void tearDownClass(){
		
		Customer tedDBehr = getBankingService().getCustomer("Ted", "D", "Behr");
		Customer shannonJMiller = getBankingService().getCustomer("Shannon", "J", "Miller");
		bs.deleteCustomer(tedDBehr);
		bs.deleteCustomer(shannonJMiller);

		em.close();
		emf.close();
		mc.close();
	}
	
	public static EntityManager getEm() {
		return em;
	}
	
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	public static BankingService getBankingService() {
		return bs;
	}

	public static MongoClient getMongoClient() {
		return mc;
	}
}

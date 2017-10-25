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
import net.scilingo.se452.banking.mongodb.MongoPropertiesLoader;

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
		//bs = BankingServiceFactory.getBankingService(em, mc);
		bs = new BankingService(em,mc);
		
		Address address = new Address();
		address.setAddressLine1("1400 S. State");
		address.setAddressLine2("APT 8-D");
		address.setCounty("Cook");
		address.setZipcode(60605);
		
		bs.createNewCustomer("Shannon", "J", "Miller", address);
		
		Customer shannonMiller = bs.getCustomer("Shannon", "J", "Miller");
		
		bs.createNewCheckingAccount(shannonMiller, 5000);
		bs.createNewSavingsAccount(shannonMiller, 10000);
		bs.createNewCDAccount(shannonMiller, 15000);
		
		Address address2 = new Address();
		address2.setAddressLine1("6250 S. Madison");
		address2.setAddressLine2("");
		address2.setCounty("Dupage");
		address2.setZipcode(60521);
		
		bs.createNewCustomer("Todd","K","Schmit", address2);
		
		Address address3 = new Address();
		address3.setAddressLine1("1700 S. Michigan");
		address3.setAddressLine2("APT 704");
		address3.setCounty("Cook");
		address3.setZipcode(60605);
		
		bs.createNewCustomer("Conrad", "H", "Jones", address3);
		
	}
	
	@AfterClass
	public static void tearDownClass(){
		
		Customer tedDBehr = getBankingService().getCustomer("Todd", "K", "Schmit");
		Customer shannonJMiller = getBankingService().getCustomer("Shannon", "J", "Miller");
		Customer conradHJones = getBankingService().getCustomer("Conrad", "H", "Jones");
		bs.deleteCustomer(tedDBehr);
		bs.deleteCustomer(shannonJMiller);
		bs.deleteCustomer(conradHJones);

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

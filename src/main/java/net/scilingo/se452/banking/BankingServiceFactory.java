package net.scilingo.se452.banking;

import javax.persistence.EntityManager;

import com.mongodb.MongoClient;

public class BankingServiceFactory {

	// Single instance of BankingService Class
	// to be shared across servlets
	private static BankingService bankingService;
	
	public static BankingService getBankingService() {
		if(bankingService == null) {
			bankingService = new BankingService();
		}
		return bankingService;
	}
	
	public static BankingService getBankingService(EntityManager entityManager, MongoClient mongoClient) {
		if(bankingService == null) {
			bankingService = new BankingService(entityManager, mongoClient);
		}
		return bankingService;
	}
}

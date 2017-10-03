package net.scilingo.se452.banking;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;

public class AddressInfoService {

	private EntityManager _entityManager;
	private static final Logger logger = Logger.getLogger(AddressInfoService.class.getName());
	
	public AddressInfoService(EntityManager entityManager) {
		this._entityManager = entityManager;
	}
	
	public AddressInfo getAddressInfo(Customer customer) {
		
		AddressInfo addressInfo = null;
		
		try {
			if(customer.getAddress().getCounty() != null && customer.getAddress().getZipcode() != 0) {
				logger.log(Level.INFO, "Finding AddressInfo where zipcode is {0} and county is {1}", new String[] {Integer.toString(customer.getAddress().getZipcode()), customer.getAddress().getCounty()});
				AddressInfoId primaryKey = new AddressInfoId(customer.getAddress().getZipcode(), customer.getAddress().getCounty());
				addressInfo = _entityManager.find(AddressInfo.class, primaryKey);
			}
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return addressInfo;
	}
	
}

package net.scilingo.se452.banking;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class AddressInfoService {

	private EntityManager _entityManager;
	private static final Logger logger = Logger.getLogger(AddressInfoService.class.getName());
	private static final String zipcodesForCountyQuery = "Select ai.zipcode from AddressInfo ai where ai.county = :county";
	private static final String countiesForZipcodeQuery = "Select ai.county from AddressInfo ai where ai.zipcode = :zipcode";
	private static final String zipcodesQuery = "Select Distinct ai.zipcode from AddressInfo ai order by 1 ASC";
	private static final String countiesQuery = "Select Distinct ai.county from AddressInfo ai order by 1 ASC";
	
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
	
	public List<Integer> getZipcodesForCounty(String county){
		Query zipcodeQuery = _entityManager.createQuery(zipcodesForCountyQuery);
		zipcodeQuery.setParameter("county", county);
		List<Integer> zipcodes = (List<Integer>) zipcodeQuery.getResultList();
		return zipcodes;
	}
	
	public List<String> getCountiesForZipCode(Integer zipcode){
		Query countiesQuery = _entityManager.createQuery(countiesForZipcodeQuery);
		countiesQuery.setParameter("zipcode", zipcode);
		List<String> counties = (List<String>) countiesQuery.getResultList();
		return counties;
	}
	
	// This is a stop-gap measure to prevent errors
	// Would have added in ajax to properly do cascading menus
	// if I had more time
	public List<String> getOnlyCookCounty(){
		List<String> counties = new ArrayList<String>();
		counties.add("Cook");
		return counties;
	}
	
	public List<Integer> getZipcodes(){
		List<Integer> zipcodes = (List<Integer>) _entityManager.createQuery(zipcodesQuery).getResultList();
		return zipcodes;
	}
	
	public List<String> getCounties(){
		List<String> counties = (List<String>) _entityManager.createQuery(countiesQuery).getResultList();
		return counties;
	}
}

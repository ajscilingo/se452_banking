package net.scilingo.se452.banking;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

public class CustomerService implements ICustomerService {

private EntityManager _entityManager;
private static final Logger logger = Logger.getLogger(CustomerService.class.getName());
private AddressInfoService _addressInfo;
	
	public CustomerService(EntityManager entityManager) {
		this._entityManager = entityManager;
		this._addressInfo = new AddressInfoService(entityManager);
	}
	
	public Customer getCustomer(Customer customer) {
		
		Customer foundCustomer = null;
		
		try {
			if(customer.getId() > 0) {
				logger.log(Level.INFO, "Finding Customer where Id is {0}", customer.getId());
				foundCustomer = _entityManager.find(Customer.class, customer.getId());
			}
		}
		catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return foundCustomer;
	}
	
	public Customer getCustomer(String firstName, String middleInitial, String lastName) {
		Query q = _entityManager.createQuery("Select c from Customer c where c.firstName = :firstName and c.middleInitial = :middleInitial and c.lastName = :lastName");
		q.setParameter("firstName", firstName);
		q.setParameter("middleInitial", middleInitial);
		q.setParameter("lastName", lastName);
		
		return (Customer) q.getSingleResult();
	}
	
	public List<Customer> getCustomers(String firstName, String middleInitial, String lastName){
		Query q = _entityManager.createQuery("Select c from Customer c where c.firstName = :firstName and c.middleInitial = :middleInitial and c.lastName = :lastName");
		q.setParameter("firstName", firstName);
		q.setParameter("middleInitial", middleInitial);
		q.setParameter("lastName", lastName);
		
		return (List<Customer>) q.getResultList();
	}
	
	
	/* (non-Javadoc)
	 * @see net.scilingo.se452.banking.ICustomerService#saveCustomer(net.scilingo.se452.banking.Customer)
	 */
	@Override
	public void saveCustomer(ICustomer customer) {
		
		EntityTransaction entityTransaction = null;
		
		try {
			entityTransaction = _entityManager.getTransaction();
		
			entityTransaction.begin();
			Customer customerToUpdate;
			Customer originalCustomer = (Customer) customer;
			customerToUpdate = getCustomer(originalCustomer);
			
			
			if(customerToUpdate != null && customerToUpdate.getId() > 0) {
				customerToUpdate.setFirstName(originalCustomer.getFirstName());
				customerToUpdate.setMiddleInitial(originalCustomer.getMiddleInitial());
				customerToUpdate.setLastName(originalCustomer.getLastName());
				customerToUpdate.setAccounts(originalCustomer.getAccounts());
				customerToUpdate.setAddress(originalCustomer.getAddress());
			}
	
			logger.log(Level.INFO, "Updating Customer {0}", customerToUpdate.toString());
			
			_entityManager.persist(customerToUpdate);
			entityTransaction.commit();
			
		}
		catch(IllegalStateException | IllegalArgumentException e) {
			
			e.printStackTrace();
			
			try {
				entityTransaction.rollback();
			}
			catch(IllegalStateException | PersistenceException | NullPointerException inner_exception) {
				inner_exception.printStackTrace();
			}
		}
		catch(RollbackException e) {
			e.printStackTrace();
		}
	}
	
	public List<Customer> getAllCustomers(){
		return (List<Customer>)_entityManager.createNamedQuery("getAllCustomers").getResultList();
	}
	
	/* (non-Javadoc)
	 * @see net.scilingo.se452.banking.ICustomerService#deleteCustomer(net.scilingo.se452.banking.Customer)
	 */
	@Override
	public void deleteCustomer(ICustomer customer) {
		
		Customer originalCustomer = (Customer) customer;
		Customer customerToDelete = getCustomer(originalCustomer);
		EntityTransaction entityTransaction = null;
		
		if(customerToDelete != null) {
			try {
				entityTransaction = _entityManager.getTransaction();
				
				entityTransaction.begin();
				logger.log(Level.INFO, "Deleting Customer {0}", customerToDelete.toString());
				_entityManager.remove(customerToDelete);
				entityTransaction.commit();
			}
			catch(IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
				e.printStackTrace();
				try {
					entityTransaction.rollback();
				}
				catch(IllegalStateException | PersistenceException | NullPointerException inner_exception) {
					inner_exception.printStackTrace();
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see net.scilingo.se452.banking.ICustomerService#createCustomer(java.lang.String, java.lang.String, java.lang.String, net.scilingo.se452.banking.Address)
	 */
	@Override
	public ICustomer createCustomer(String firstName, String middleInitial, String lastName, IAddress address) {
		
		EntityTransaction entityTransaction = null;
		Customer customer = new Customer();
		
		customer.setFirstName(firstName);
		customer.setMiddleInitial(middleInitial);
		customer.setLastName(lastName);
		customer.setAddress((Address)address);
		customer.setAddressInfo(this._addressInfo.getAddressInfo(customer));
		
		try {
			entityTransaction = _entityManager.getTransaction();
			
			entityTransaction.begin();
			_entityManager.persist(customer);
			entityTransaction.commit();
			
			logger.log(Level.INFO, "Creating Customer {0}, {1}, {2}", new String[] {firstName, middleInitial, lastName});
		}
		catch(IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			e.printStackTrace();
			try {
				entityTransaction.rollback();
			}
			catch(IllegalStateException | PersistenceException | NullPointerException inner_exception) {
				inner_exception.printStackTrace();
			}
		}
		
		return customer;
	}
}

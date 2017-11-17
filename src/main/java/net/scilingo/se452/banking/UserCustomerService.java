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

import com.mysql.jdbc.StringUtils;

public class UserCustomerService {

	private EntityManager _entityManager;
	private static final Logger logger = Logger.getLogger(UserCustomerService.class.getName());
	private static final String userCustomerForUsernameQuery = "Select uc.customer from UserCustomer uc where uc.username = :username";
	
	public UserCustomerService(EntityManager entityManager) {
		this._entityManager = entityManager;
	}

	public UserCustomer getUserCustomer(UserCustomer userCustomer) {

		UserCustomer foundUserCustomer = null;

		try {
			if (!StringUtils.isNullOrEmpty(userCustomer.getUsername())) {
				logger.log(Level.INFO, "Finding UserCustomer where username is {0}", userCustomer.getUsername());
				foundUserCustomer = _entityManager.find(UserCustomer.class, userCustomer.getUsername());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return foundUserCustomer;
	}

	public void saveUserCustomer(UserCustomer userCustomer) {

		EntityTransaction entityTransaction = null;

		try {
			entityTransaction = _entityManager.getTransaction();
			entityTransaction.begin();
			UserCustomer userCustomerToUpdate;
			userCustomerToUpdate = getUserCustomer(userCustomer);

			if (userCustomerToUpdate != null) {
				userCustomerToUpdate.setUsername(userCustomer.getUsername());
				userCustomerToUpdate.setCustomer(userCustomer.getCustomer());
			}

			_entityManager.persist(userCustomerToUpdate);
			entityTransaction.commit();
			logger.log(Level.INFO, "Updating UserCustomer with {0} username and {1} customer id",
					new String[] { userCustomer.getUsername(), userCustomer.getCustomer().getId().toString() });
		} catch (IllegalStateException | IllegalArgumentException e) {

			e.printStackTrace();

			try {
				entityTransaction.rollback();
			} catch (IllegalStateException | PersistenceException | NullPointerException inner_exception) {
				inner_exception.printStackTrace();
			}
		} catch (RollbackException e) {
			e.printStackTrace();
		}
	}

	public List<UserCustomer> getAllUserCustomers() {
		return (List<UserCustomer>) _entityManager.createNamedQuery("getAllUserCustomers").getResultList();
	}

	public void deleteUserCustomer(UserCustomer userCustomer) {

		UserCustomer userCustomerToDelete = getUserCustomer(userCustomer);
		EntityTransaction entityTransaction = null;

		if (userCustomerToDelete != null) {

			try {
				entityTransaction = _entityManager.getTransaction();

				logger.log(Level.INFO, "Deleting UserCustomer with {0} username and {1} customer id ",
						new String[] { userCustomer.getUsername(), userCustomer.getCustomer().getId().toString() });

				entityTransaction.begin();
				_entityManager.remove(userCustomerToDelete);
				entityTransaction.commit();
			} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
				e.printStackTrace();
				try {
					entityTransaction.rollback();
				} catch (IllegalStateException | PersistenceException | NullPointerException inner_exception) {
					inner_exception.printStackTrace();
				}
			}
		}
	}

	public UserCustomer createUserCustomer(UserCustomer userCustomer) {
		
		EntityTransaction entityTransaction = null;
		
		try {
			entityTransaction = _entityManager.getTransaction();
			
			entityTransaction.begin();
			_entityManager.persist(userCustomer);
			entityTransaction.commit();
			
			logger.log(Level.INFO, "Creating UserCustomer with {0} username and {1} customer id ",
					new String[] { userCustomer.getUsername(), userCustomer.getCustomer().getId().toString() });
			
		} catch(IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			e.printStackTrace();
			try {
				entityTransaction.rollback();
			}
			catch(IllegalStateException | PersistenceException | NullPointerException inner_exception) {
				inner_exception.printStackTrace();
			}
		}
		
		return userCustomer;
	}
	
	public Customer getCustomerForUsername (String username) {
		Query query = _entityManager.createQuery(userCustomerForUsernameQuery);
		query.setParameter("username", username);
		Customer customer = null;
		
		try {
			customer = (Customer) query.getSingleResult();
		} catch(Exception e) {
			logger.log(Level.WARNING, e.getMessage());
		}
		
		return customer;
	}
}

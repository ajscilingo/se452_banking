package net.scilingo.se452.banking;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;

public class AccountService {

	private EntityManager _entityManager;
	private static final Logger logger = Logger.getLogger(AccountService.class.getName());

	public AccountService(EntityManager entityManager) {
		this._entityManager = entityManager;
	}

	public Account getAccount(Account account) {

		Account foundAccount = null;

		try {
			if (account.getId() > 0) {
				logger.log(Level.INFO, "Finding Account where Id is {0}", account.getId());
				foundAccount = _entityManager.find(Account.class, account.getId());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return foundAccount;
	}

	public void saveAccount(Account account) {

		EntityTransaction entityTransaction = null;

		try {
			entityTransaction = _entityManager.getTransaction();

			entityTransaction.begin();
			Account accountToUpdate;
			accountToUpdate = getAccount(account);

			if (accountToUpdate != null && accountToUpdate.getId() > 0) {
				accountToUpdate.setAccountType(account.getAccountType());
				accountToUpdate.setBalance(account.getBalance());
				accountToUpdate.setCustomer(account.getCustomer());
				logTransaction(account);
			}

			_entityManager.persist(accountToUpdate);
			entityTransaction.commit();
			logger.log(Level.INFO, "Updating {0} account for {1} with balance of {2}",
					new String[] { account.getAccountType().toString(), account.getCustomer().toString(),
							Integer.toString(account.getBalance()) });

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

	public List<Account> getAllAccounts() {
		return (List<Account>) _entityManager.createNamedQuery("getAllAccounts").getResultList();
	}

	public void deleteAccount(Account account) {

		Account accountToDelete = getAccount(account);
		EntityTransaction entityTransaction = null;

		if (accountToDelete != null) {
			try {
				entityTransaction = _entityManager.getTransaction();

				logger.log(Level.INFO, "Deleting {0} account for {1} with balance of {2}",
						new String[] { accountToDelete.getAccountType().toString(),
								accountToDelete.getCustomer().toString(),
								Integer.toString(accountToDelete.getBalance()) });
				entityTransaction.begin();
				_entityManager.remove(accountToDelete);
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

	public void createNewAccount(Customer customer, int balance, AccountType accountType) {

		EntityTransaction entityTransaction = null;
		Account account = new Account();

		account.setAccountType(accountType);
		account.setBalance(balance);
		// Need to link both ways otherwise delete/update won't work.
		account.setCustomer(customer);
		account.getCustomer().getAccounts().add(account);
		logTransaction(account);

		try {
			entityTransaction = _entityManager.getTransaction();

			entityTransaction.begin();
			_entityManager.persist(account);
			entityTransaction.commit();
			logger.log(Level.INFO, "Creating new {0} account for {1} with starting balance of {2}",
					new String[] { accountType.toString(), customer.toString(), Integer.toString(balance) });
		} catch (IllegalStateException | IllegalArgumentException | TransactionRequiredException e) {
			e.printStackTrace();
			try {
				entityTransaction.rollback();
			} catch (IllegalStateException | PersistenceException | NullPointerException inner_exception) {
				inner_exception.printStackTrace();
			}
		}
	}

	private void logTransaction(Account account) {
		
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setAmount(account.getBalance());
		account.transactions.add(transaction);
		
	}
}

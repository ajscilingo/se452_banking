package net.scilingo.se452.banking.interfaces;

import java.util.Set;

import net.scilingo.se452.banking.AccountType;
import net.scilingo.se452.banking.Transaction;

public interface IAccount {
	
	public Integer getId();
	public Integer getBalance();
	public AccountType getAccountType();
	public Set<Transaction> getTransactions();
	
	public void setId(Integer id);
	public void setBalance(Integer balance);
	public void setAccountType(AccountType accountType);
	public void setTransactions(Set<Transaction> transactions);
	
	
	
	
	
}

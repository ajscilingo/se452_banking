package net.scilingo.se452.banking;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.scilingo.se452.banking.interfaces.IAccount;

@Entity
@Table(name="ACCOUNT")
@NamedQuery(name= "getAllAccounts", query= "select a from Account a")
public class Account implements IAccount {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="CUSTOMER_ID")
	Customer customer;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="account", cascade=CascadeType.ALL, orphanRemoval=true)
	Set<Transaction> transactions = new HashSet<Transaction>();
	
	@Column(name="BALANCE")
	private int balance;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="ACCOUNT_TYPE")
	private AccountType accountType;
	
	
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getBalance() {
		return balance;
	}

	@Override
	public AccountType getAccountType() {
		return accountType;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setBalance(int balance) {
		this.balance = balance;
		
	}

	@Override
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public Customer getCustomer() {
		return this.customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	@Override
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}
	
}

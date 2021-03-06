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
	private Integer id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="CUSTOMER_ID")
	Customer customer;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="account", cascade=CascadeType.ALL, orphanRemoval=true)
	Set<Transaction> transactions = new HashSet<Transaction>();
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="account", cascade=CascadeType.ALL, orphanRemoval=true)
	Set<Deposit> deposits = new HashSet<Deposit>();
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="account", cascade=CascadeType.ALL, orphanRemoval=true)
	Set<Withdraw> withdraws = new HashSet<Withdraw>();
	
	@Column(name="BALANCE")
	private Double balance;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="ACCOUNT_TYPE")
	private AccountType accountType;
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Double getBalance() {
		return balance;
	}

	@Override
	public AccountType getAccountType() {
		return accountType;
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setBalance(Double balance) {
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
	
	@Override
	public Set<Deposit> getDeposits() {
		return deposits;
	}

	@Override
	public void setDeposits(Set<Deposit> deposits) {
		this.deposits = deposits;
	}

	@Override
	public Set<Withdraw> getWithdraws() {
		return withdraws;
	}

	@Override
	public void setWithdraws(Set<Withdraw> withdraws) {
		this.withdraws = withdraws;
	}
}

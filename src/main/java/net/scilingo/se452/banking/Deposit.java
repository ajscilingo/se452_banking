package net.scilingo.se452.banking;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.scilingo.se452.banking.interfaces.IAccount;
import net.scilingo.se452.banking.interfaces.ITransaction;

@Entity
@Table(name="DEPOSIT")
public class Deposit implements ITransaction{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@Column(name="TIME", insertable=false, updatable=false, nullable=false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date time;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public IAccount getAccount() {
		return account;
	}

	@Override
	public Double getAmount() {
		return amount;
	}

	@Override
	public Date getTime() {
		return time;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void setAccount(IAccount account) {
		this.account = (Account) account;
	}

	@Override
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}

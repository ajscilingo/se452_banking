package net.scilingo.se452.banking;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TRANSACTION")
public class Transaction implements ITransaction, Serializable {

	private static final long serialVersionUID = 8680554510522530393L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="ACCOUNT_ID")
	private Account account;
	
	@Column(name="AMOUNT")
	private int amount;
	
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
	public int getAmount() {
		return amount;
	}

	@Override
	public Date getTime() {
		return time;
	}

	@Override
	public void setAccount(IAccount account) {
		this.account = (Account) account;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}

}

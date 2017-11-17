package net.scilingo.se452.banking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER_CUSTOMER")
@NamedQuery(name= "getAllUserCustomers", query= "select uc from UserCustomer uc")
public class UserCustomer {

	@Id
	@Column(name="username")
	private String username;
	
	@OneToOne(optional=false)
	@JoinColumn(name="customer_id")
	Customer customer;
	
	
	public String getUsername() {
		return username;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}

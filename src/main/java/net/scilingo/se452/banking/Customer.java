package net.scilingo.se452.banking;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.scilingo.se452.banking.interfaces.IAddress;
import net.scilingo.se452.banking.interfaces.ICustomer;

@Entity
@Table(name="CUSTOMER")
@NamedQuery(name= "getAllCustomers", query= "select c from Customer c")
public class Customer implements ICustomer, Serializable {

	private static final long serialVersionUID = 618538680042609584L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	
	@Column(name = "F_NAME")
	private String firstName;
	
	@Column(name = "M_INITIAL")
	private String middleInitial;
	
	@Column(name = "L_NAME")
	private String lastName;
	
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="customer", cascade=CascadeType.ALL, orphanRemoval=true)
	private Set<Account> accounts = new HashSet<Account>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns( value = {@JoinColumn(referencedColumnName = "ZIPCODE", name="ZIPCODE", updatable = false, insertable = false, nullable = false),
				 @JoinColumn(referencedColumnName = "COUNTY", name="COUNTY", updatable = false, insertable = false)})
	private AddressInfo addressInfo;
	
	@Embedded
	private Address address;
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getMiddleInitial() {
		return middleInitial;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public IAddress getAddress() {
		return this.address;
	}

	public void setAddress(IAddress address) {
		this.address = (Address) address;
	}
	
	public Set<Account> getAccounts(){
		return this.accounts;
	}
	
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public AddressInfo getAddressInfo() {
		return this.addressInfo;
	}
	
	public void setAddressInfo(AddressInfo addressInfo) {
		this.addressInfo = addressInfo;
	}
	
	@Override
	public String toString() {
		return firstName + " " + middleInitial + " " + lastName;
	}
	
}

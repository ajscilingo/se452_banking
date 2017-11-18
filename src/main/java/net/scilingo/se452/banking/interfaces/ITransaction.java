package net.scilingo.se452.banking.interfaces;


public interface ITransaction {

	public int getId();
	public IAccount getAccount();
	public Double getAmount();
	public java.sql.Date getTime();
	
	public void setId(int id);
	public void setAccount(IAccount account);
	public void setAmount(Double amount);
	
}

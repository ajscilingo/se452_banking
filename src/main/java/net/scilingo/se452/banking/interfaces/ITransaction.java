package net.scilingo.se452.banking.interfaces;


public interface ITransaction {

	public int getId();
	public IAccount getAccount();
	public int getAmount();
	public java.sql.Date getTime();
	
	public void setAccount(IAccount account);
	public void setAmount(int amount);
	
}

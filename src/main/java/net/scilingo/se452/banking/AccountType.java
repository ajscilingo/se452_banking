package net.scilingo.se452.banking;

public enum AccountType {
	ZERO,
	CHECKING,
	SAVINGS,
	CD,
	RESERVED;

	public String getDisplayName() {
		if(!name().equals("CD")) {
			String displayName = new StringBuilder(name().substring(0,1).toUpperCase()).append(name().substring(1,name().length()).toLowerCase()).toString();
			return displayName;
		}
		else 
			return name();
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
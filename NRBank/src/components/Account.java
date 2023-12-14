//1.2.1 Creation of the account class

package components;

public abstract class Account {
	
	protected String label;
	protected Double balance;
	protected int accountNumber;
	protected Clients client;
	
	public Account(String label, Clients client, int accountNumber) {
		
		this.label = label;
		this.client = client;
		this.accountNumber = ++accountNumber;
		
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public Clients getClient() {
		return client;
	}

	public void setClient(Clients client) {
		this.client = client;
	}

	@Override
	public String toString() {
		return label + balance + accountNumber + client;
	}
	
	

}

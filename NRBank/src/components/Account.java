//1.2.1 Creation of the account class

package components;

public abstract class Account {
	
	protected String label;
	protected Double balance;
	protected static int nextAccountNumber = 1;
	protected int accountNumber;
	protected Clients client;
	
	public Account(String label, Clients client) {
		
		this.label = label;
		this.client = client;
		this.accountNumber = nextAccountNumber++;
		this.balance = 0.0;
		
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
	
	//1.3.5 Updating accounts

	public void setBalance(Flow flow) {
		double amount = flow.getAmount();
		boolean isEffect = flow.isEffect();
		
		if(flow instanceof Credit) {
			this.balance += amount;
		} else if (flow instanceof Debit){
			this.balance -= amount;
		} else if(flow instanceof Transfer) {
			Transfer transfer = (Transfer) flow;
			int targetAccountNumber = transfer.getTargetAccountNumber();
			int issuingAccountNumber = transfer.getAccountNumber();
			
			if (this.accountNumber == targetAccountNumber) {
				this.balance += amount;
			} else if (this.accountNumber == issuingAccountNumber) {
				this.balance -= amount;
			}
		}
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
		return label + " Balance: " + balance + " " + accountNumber + client;
	}
	
	

}

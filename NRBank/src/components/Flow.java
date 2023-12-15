//1.3.2 Creation of the Flow class

package components;

import java.util.Date;

public abstract class Flow {
	
	private String comment;
	private int identifier;
	private static int nextIdentifier = 1;
	private double amount;
	private int targetAccountNumber;
	private boolean effect;
	private Date dateFlow;
	
	public Flow(String comment, double amount, int targetAccountNumber, boolean effect, Date dateFlow) {
	
		this.comment = comment;
		this.identifier = ++nextIdentifier;
		this.amount = amount;
		this.targetAccountNumber = targetAccountNumber;
		this.effect = effect;
		this.dateFlow = dateFlow;
		
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTargetAccountNumber() {
		return targetAccountNumber;
	}

	public void setTargetAccountNumber(int targetAccountNumber) {
		this.targetAccountNumber = targetAccountNumber;
	}

	public boolean isEffect() {
		return effect;
	}

	public void setEffect(boolean effect) {
		this.effect = effect;
	}

	public Date getDateFlow() {
		return dateFlow;
	}

	public void setDateFlow(Date dateFlow) {
		this.dateFlow = dateFlow;
	}
	
	
	
}

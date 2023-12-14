//1.3.2 Creation of the Transfer class

package components;

import java.util.Date;

public class Transfer extends Flow {

	public Transfer(String comment, int identifier, double amount, int targetAccountNumber, boolean effect,
			Date dateFlow) {
		super(comment, identifier, amount, targetAccountNumber, effect, dateFlow);
	}

	

}

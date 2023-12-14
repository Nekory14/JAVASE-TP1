//1.3.2 Creation of the Debit class

package components;

import java.util.Date;

public class Debit extends Flow {

	public Debit(String comment, int identifier, double amount, int targetAccountNumber, boolean effect,
			Date dateFlow) {
		super(comment, identifier, amount, targetAccountNumber, effect, dateFlow);
	}

}

//1.3.2 Creation of the Credit class

package components;

import java.util.Date;

public class Credit extends Flow {

	public Credit(String comment, int identifier, double amount, int targetAccountNumber, boolean effect,
			Date dateFlow) {
		super(comment, identifier, amount, targetAccountNumber, effect, dateFlow);
	}

}

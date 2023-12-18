//1.1.1 Creation of the Client class

package components;

public class Clients {
	
	private static int clientCounter = 0;
	
	private String name;
	private String firstName;
	private int clientNumber;
	
	public Clients(String name, String firstName) {
	
		this.name = name;
		this.firstName = firstName;
		this.clientNumber = ++clientCounter;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public int getClientNumber() {
		return clientNumber;
	}
	
	@Override
	public String toString() {
		return "Client: " + clientNumber + " - " + name + " " + firstName;
	}
	
}

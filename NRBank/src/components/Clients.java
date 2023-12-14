//1.1.1 Creation of the Client class

package components;

public class Clients {
	
	private String name;
	private String firstName;
	private static int clientNumber = 0;
	
	public Clients(String name, String firstName) {
	
		this.name = name;
		this.firstName = name;
		clientNumber++;
		
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

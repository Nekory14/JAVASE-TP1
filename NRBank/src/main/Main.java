//1.1.2 Creation of main class for tests

package main;

import java.util.ArrayList;

import components.Clients;

public class Main {
	
	public static void main(String[] args) {
				
		ArrayList<Clients> clientsList = new ArrayList<>();
		
		loadClients(clientsList, 3, "John", "Doe");
		
		displayClients(clientsList);
		
	}
	
	
	public static void loadClients(ArrayList<Clients> clientsList, int numberOfClients, String name, String firstName) {
		clientsList.addAll(generateClients(name, firstName, numberOfClients));
	}
	
	public static ArrayList<Clients> generateClients(String name, String firstName, int numberOfClients) {
		
		ArrayList<Clients> clientSet = new ArrayList<>();
		for (int i = 0; i < numberOfClients; i++) {
			clientSet.add(new Clients("Name: " + name, " Firstname: " + firstName, i));
		}
		return clientSet;
		
	}
	
	public static void displayClients(ArrayList<Clients> clientsList) {
        clientsList.stream()
                .map(Clients::toString)
                .forEach(System.out::println);
    }
	
	
}

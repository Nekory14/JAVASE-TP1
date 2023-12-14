//1.1.2 Creation of main class for tests

package main;

import java.util.ArrayList;

import components.Account;
import components.Clients;
import components.CurrentAccount;
import components.SavingsAccount;

public class Main {
	
	public static void main(String[] args) {
				
		ArrayList<Clients> clientsList = new ArrayList<>();
		
		loadClients(clientsList, 3, "John", "Doe");
		
		displayClients(clientsList);
		
		ArrayList<Account> accountsList = loadAccounts(clientsList);
		
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
	
	//1.2.3 Creation of the table account
	
	public static ArrayList<Account> loadAccounts(ArrayList<Clients> clientsList) {
	    ArrayList<Account> accountsList = new ArrayList<>();
	    for (Clients client : clientsList) {
	        accountsList.add(new SavingsAccount(client));
            accountsList.add(new CurrentAccount(client));

	    }
	    
	    return accountsList;
	}
	
	public static void displayAccounts(ArrayList<Account> accountsList) {
        accountsList.stream()
                .map(Account::toString)
                .forEach(System.out::println);
    }
	
	
}

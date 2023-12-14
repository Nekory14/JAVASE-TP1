//1.1.2 Creation of main class for tests

package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Map;

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
		
		Hashtable<Integer, Account> accountHashtable = createAccountHashtable(accountsList);
		
		displayAccountHashtable(accountHashtable);
		
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
	
	//1.3.1 Adaptation of the table of accounts
	
	public static Hashtable<Integer, Account> createAccountHashtable(ArrayList<Account> accountsList){
		Hashtable<Integer, Account> accountHashtable = new Hashtable<>();
		
		for (Account account : accountsList) {
			accountHashtable.put(account.getAccountNumber(), account);
		}
		
		return accountHashtable;
	}
	
	public static void displayAccountHashtable(Hashtable<Integer, Account> accountHashtable) {
		accountHashtable.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.comparing(Account::getBalance)))
				.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
	}
	
	
}

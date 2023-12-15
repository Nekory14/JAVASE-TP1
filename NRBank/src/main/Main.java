//1.1.2 Creation of main class for tests

package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import components.Account;
import components.Clients;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.SavingsAccount;
import components.Transfer;

public class Main {
	
	public static void main(String[] args) {
				
		ArrayList<Clients> clientsList = new ArrayList<>();
		
		loadClients(clientsList, 3, "John", "Doe");
		
		displayClients(clientsList);
		
		ArrayList<Account> accountsList = loadAccounts(clientsList);
		
		Hashtable<Integer, Account> accountHashtable = createAccountHashtable(accountsList);
		
		displayAccountHashtable(accountHashtable);
		
		ArrayList<Flow> flowsList = loadFlows(accountsList);
		
		displayFlows(flowsList);
		
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
	
	//1.3.4 Creation of the flow array
	
	public static ArrayList<Flow> loadFlows(ArrayList<Account> accountsList){
		ArrayList<Flow> flowsList = new ArrayList<>();
		
		Debit debit1 = new Debit("Debit of 50€", 50.0, 1, true, currentDatePlusTwoDays());
		flowsList.add(debit1);
		
		for (Account account : accountsList) {
			if (account instanceof CurrentAccount) {
				Credit credit1 = new Credit("Credit of 100.50€", 100.50, account.getAccountNumber(), true, currentDatePlusTwoDays());
				flowsList.add(credit1);
			} else if (account instanceof SavingsAccount) {
				Credit credit2 = new Credit("Credit of 1500€", 1500.0, account.getAccountNumber(), true, currentDatePlusTwoDays());
				flowsList.add(credit2);
			}
		}
		
		Transfer transfer1 = new Transfer("Transfer of 50€", 50.0, 1, 2, true, currentDatePlusTwoDays());
		flowsList.add(transfer1);
		
		
		return flowsList;
	}
	
	public static void displayFlows(ArrayList<Flow> flowsList) {
		flowsList.stream()
				.map(Flow::toString)
				.forEach(System.out::println);
	}
	
	public static Date currentDatePlusTwoDays() {
		LocalDate currentDate = LocalDate.now();
		LocalDate currentDatePlusTwo = currentDate.plusDays(2);
		return java.sql.Date.valueOf(currentDatePlusTwo);
	}
	
	
}

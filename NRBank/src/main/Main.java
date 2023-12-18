//1.1.2 Creation of main class for tests

package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

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
				
		ArrayList<Flow> flowsList = loadFlows(accountsList);
		
		displayFlows(flowsList);
		
		updateBalances(flowsList, accountHashtable);
		
		displayAccountHashtable(accountHashtable);
		
		readJson(flowsList);
		
		displayFlows(flowsList);

		readXML(clientsList, accountsList);
		
		displayClients(clientsList);
	}
	
	
	public static void loadClients(ArrayList<Clients> clientsList, int numberOfClients, String name, String firstName) {
		clientsList.addAll(generateClients(name, firstName, numberOfClients));
	}
	
	public static ArrayList<Clients> generateClients(String name, String firstName, int numberOfClients) {
		
		ArrayList<Clients> clientSet = new ArrayList<>();
		for (int i = 0; i < numberOfClients; i++) {
			clientSet.add(new Clients("Name: " + name, " Firstname: " + firstName));
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
				.forEach(entry -> System.out.println(entry.getValue()));
	}
	
	//1.3.4 Creation of the flow array
	
	public static ArrayList<Flow> loadFlows(ArrayList<Account> accountsList){
		ArrayList<Flow> flowsList = new ArrayList<>();
		
		Debit debit1 = new Debit("Debit of 50€", 50.0, 1, true, currentDatePlusTwoDays());
		flowsList.add(debit1);
		
		for (Account account : accountsList) {
			if (account instanceof CurrentAccount) {
				Credit credit1 = new Credit("Credit of 100.50€", 100.50, account.getAccountNumber(), true, new Date());
				flowsList.add(credit1);
			} else if (account instanceof SavingsAccount) {
				Credit credit2 = new Credit("Credit of 1500€", 1500.0, account.getAccountNumber(), true, new Date());
				flowsList.add(credit2);
			}
		}
		
		Transfer transfer1 = new Transfer("Transfer of 50€", 50.0, 1, 2, true, new Date());
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
	
	public static void updateBalances(ArrayList<Flow> flowsList, Map<Integer, Account> accountHashtable) {
		for (Flow flow : flowsList) {
			int targetAccountNumber = flow.getTargetAccountNumber();
			
			if (accountHashtable.containsKey(targetAccountNumber)) {
				Account account = accountHashtable.get(targetAccountNumber);
				account.setBalance(flow);
			}
			
			if (flow instanceof Transfer) {
			    Transfer transfer = (Transfer) flow;
			    int issuingAccountNumber = transfer.getAccountNumber();
			    
			    if (accountHashtable.containsKey(issuingAccountNumber)) {
			        Account issuingAccount = accountHashtable.get(issuingAccountNumber);
			        issuingAccount.setBalance(flow);
			    }
			}
		}
		
		Optional<Account> accountWithNegativeBalance = accountHashtable.values().stream()
				.filter(account -> account.getBalance() < 0)
				.findFirst();
		
		accountWithNegativeBalance.ifPresent(account ->
		System.out.println("Warning: Account " + account.getAccountNumber() + " has a negative balance: " + account.getBalance()));
		
	}
	
	//2.1 JSON file of flows
	
	public static void readJson(ArrayList<Flow> flowsList) {
		
		Path path = FileSystems.getDefault().getPath("..\\flows.json");
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(path);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		Type listType = new TypeToken<List<Map<String, Object>>>(){}.getType();
		List<Map<String, Object>> list = new Gson().fromJson(reader, listType);
		
		for(Map<String,Object> properties : list) {
			
			if (properties.get("comment").toString().startsWith("Debit")) {
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateFlow = null;
				
				try {
					dateFlow = dateFormat.parse(properties.get("dateFlow").toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Debit flow = new Debit (properties.get("comment").toString(), 
										Double.parseDouble(properties.get("amount").toString()), 
										(int) Double.parseDouble(properties.get("targetAccountNumber").toString()), 
										Boolean.parseBoolean(properties.get("effect").toString()), 
										dateFlow);
				flowsList.add(flow);

			} else if (properties.get("comment").toString().startsWith("Credit")){
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateFlow = null;
				
				try {
					dateFlow = dateFormat.parse(properties.get("dateFlow").toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Credit flow = new Credit (properties.get("comment").toString(), 
										Double.parseDouble(properties.get("amount").toString()), 
										(int) Double.parseDouble(properties.get("targetAccountNumber").toString()), 
										Boolean.parseBoolean(properties.get("effect").toString()), 
										dateFlow);
				flowsList.add(flow);
				
			} else if (properties.get("comment").toString().startsWith("Transfer")) {
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateFlow = null;
				
				try {
					dateFlow = dateFormat.parse(properties.get("dateFlow").toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				Transfer flow = new Transfer (properties.get("comment").toString(), 
										Double.parseDouble(properties.get("amount").toString()),
										(int) Double.parseDouble(properties.get("accountNumber").toString()) ,
										(int) Double.parseDouble(properties.get("targetAccountNumber").toString()), 
										Boolean.parseBoolean(properties.get("effect").toString()), 
										dateFlow);
				flowsList.add(flow);
			}
			
		}

	}
	
	//2.2 XML file of account
	
	public static void readXML(ArrayList<Clients> clientsList, ArrayList<Account> accountsList) {

		try {
			Path path = Paths.get("..\\accounts.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(path.toFile());
			
			NodeList accountNodes = document.getElementsByTagName("account");
			
			for(int i = 0; i < accountNodes.getLength(); i++) {
				
				Element accountElement = (Element) accountNodes.item(i);
				String label = accountElement.getElementsByTagName("label").item(0).getTextContent();
				
				Element clientElement = (Element) accountElement.getElementsByTagName("client").item(0);
				String name = clientElement.getElementsByTagName("name").item(0).getTextContent();
				String firstName = clientElement.getElementsByTagName("firstName").item(0).getTextContent();
				
				Clients client = new Clients("Name: " + name, "Firstname: " + firstName);
				
				Account account;
				
				if("Savings Account".equals(label)) {
					account = new SavingsAccount(client);
				} else if ("Current Account".equals(label)) {
					account = new CurrentAccount(client);
				} else {
					continue;
				}
				
				clientsList.add(client);
				accountsList.add(account);
				
			}
			

			
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}
	
}

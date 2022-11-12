package passwordManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class DataManager implements IDataManager {
	/*
	 * Finds Downloads directory
	 */
	private static final String DOWNLOADS_PATH = Paths.get(System.getProperty("user.home"), "Downloads").toString();

	private int signedInUser;
	private HashMap<Integer, Account> existingAccounts;
	private HashMap<Integer, HashMap<String, String>> existingCredentials;
	private static ArrayList<Integer> existingUserIds;

	// Getters & setters
	
	public int getSignedInUser() {
		return signedInUser;
	}

	public void setSignedInUser(int signedInUser) {
		this.signedInUser = signedInUser;
	}

	public HashMap<Integer, Account> getExistingAccounts() {
		return existingAccounts;
	}

	public void setExistingAccounts(HashMap<Integer, Account> existingAccounts) {
		this.existingAccounts = existingAccounts;
	}

	public HashMap<Integer, HashMap<String, String>> getExistingCredentials() {
		return existingCredentials;
	}

	public void setExistingCredentials(HashMap<Integer, HashMap<String, String>> existingCredentials) {
		this.existingCredentials = existingCredentials;
	}

	public static ArrayList<Integer> getExistingUserIds() {
		return existingUserIds;
	}

	public static void setExistingUserIds(ArrayList<Integer> existingUserIds) {
		DataManager.existingUserIds = existingUserIds;
	}
	
	// Methods

	public final void readAccountData() {
		HashMap<Integer, Account> unsortedAccountMap = new HashMap<>();
		String path = DOWNLOADS_PATH + "/userAccounts.txt";
		String currentLine, name, password;
		int userId;

		try {
			File accountDataFile = new File(path);
			
			if (!accountDataFile.exists()) {
				try {
					accountDataFile.createNewFile();
				} catch (IOException e) {
					System.out.println("Accounts database could not be created."
							+ "\nPossible fix: restart the program.");
				}
			}
			Scanner sc = new Scanner(accountDataFile);

			while (sc.hasNextLine()) {
				currentLine = sc.nextLine();
				int indexOfFirstSpace = currentLine.indexOf(" "), indexOfLastSpace = currentLine.lastIndexOf(" ");
				if (currentLine.charAt(0) == '{' || currentLine.charAt(0) == '}') {
					continue;
				} else if (currentLine.length() > 1) {
					userId = Integer.parseInt(currentLine.substring(7, indexOfFirstSpace));
					name = currentLine.substring(indexOfFirstSpace + 6, indexOfLastSpace);
					password = currentLine.substring(indexOfLastSpace + 10);
					unsortedAccountMap.put(userId, new Account(userId, name, password));
				} else {
					sc.close();
					break;
				}
			}
			ArrayList<Map.Entry<Integer, Account>> listToSort = new ArrayList<>();
			
			for (Map.Entry<Integer, Account> entry : unsortedAccountMap.entrySet()) {
				listToSort.add(entry);
			}
						
			Comparator<Map.Entry<Integer, Account>> entryComparator = new Comparator<Map.Entry<Integer, Account>>() {

				@Override
				public int compare(Entry<Integer, Account> o1, Entry<Integer, Account> o2) {
					int accountNameComparison = o1.getValue().getName().compareTo(o2.getValue().getName());

					if (accountNameComparison != 0) {
						return accountNameComparison;
					} else {
						if (o1.getKey() > o2.getKey()) {
							return 1;
						} else if (o1.getKey() < o2.getKey()) {
							return -1;
						} else {
							return 0;
						}
					}
				}
			};
			
			Collections.sort(listToSort, entryComparator);
			HashMap<Integer, Account> sortedAccountMap = new HashMap<>();
			
			for (int i = 0; i < listToSort.size(); i++)
				sortedAccountMap.put(listToSort.get(i).getKey(), listToSort.get(i).getValue());
						
			setExistingAccounts(sortedAccountMap);
			retrieveAndSetUsedIds();
		} catch (FileNotFoundException e) {
			System.out.println("Accounts database could not be found."
					+ "\nPossible fix: restart the program.");
		}
	}

	public final void writeAccountData(Account acc) {
		try {
			FileWriter writer = new FileWriter(DOWNLOADS_PATH + "/userAccounts.txt", true);
			writer.append("{\n" + acc.toString() + "\n}\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("An issue has occured when trying to update the accounts database.");
		}
	}
	
	public final void addCredentialSetToDB(int userId, Credentials credentialSet) {

		File credentialsFile = new File(DOWNLOADS_PATH + "/credentials.txt");
		String entireFile = "";

		try {
			Scanner sc = new Scanner(credentialsFile);

			while (sc.hasNextLine()) {
				entireFile += sc.nextLine();
				entireFile += "\n";
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("Credentials database could not be found."
					+ "\nPossible fix: restart the program.");
		}

		if (entireFile.contains("+" + Integer.toString(userId))) {
			for (int i = 0; i < entireFile.length(); i++) {
				// If ID is on DB
				if (entireFile.substring(i, i + 1).equals("+") && entireFile.substring(i + 1, i + 6).equals(Integer.toString(userId))) {
					i += 7; // to skip over user id line
					entireFile = entireFile.substring(0, i + 2) + credentialSet.toString() + entireFile.substring(i + 1);
					break;
				}
			}
		} else {
			// If not on DB: (adds userId entry)
			entireFile += "nextUser\n+" + Integer.toString(userId) + "\n{\n" + credentialSet.toString() + "\n}";
		}
		
		try { // writes to file
			FileWriter writer = new FileWriter(DOWNLOADS_PATH + "/credentials.txt");
			writer.write(entireFile);
			writer.close();
		} catch (IOException e) {
			System.out.println("An issue has occured when trying to update the credentials database.");
		}
	}

	public final void readCredentialsFile() {
		HashMap<Integer, HashMap<String, String>> credentialsMap = new HashMap<Integer, HashMap<String, String>>();

		try {
			File credentialsFile = new File(DOWNLOADS_PATH + "/credentials.txt");
			
			if (!credentialsFile.exists()) {
				try {
					credentialsFile.createNewFile();
				} catch (IOException e) {
					System.out.println("Credentials database could not be created."
							+ "\nPossible fix: restart the program.");
				}
			}
			Scanner sc;
			String currentLine, username, password, friendlyName;
			int userId = 0, indexOfFirstSpace, indexOfLastSpace;
			HashMap<String, String> credentials;
			sc = new Scanner(credentialsFile);
			boolean done = false;

			while (!done) {
				credentials = new HashMap<String, String>();

				if (!sc.hasNextLine() || sc.nextLine() == "") {
					done = true;
					continue;
				}
				while (sc.hasNextLine()) {
					currentLine = sc.nextLine();

					if (currentLine.charAt(0) == '+') {
						userId = Integer.parseInt(currentLine.substring(1));
					} else if (currentLine.charAt(0) == '}') {
						credentialsMap.put(userId, credentials);
						break;
					} else if (currentLine.charAt(0) == '-') {
						indexOfFirstSpace = currentLine.indexOf(" ");
						indexOfLastSpace = currentLine.lastIndexOf(" ");
						friendlyName = currentLine.substring(1, indexOfFirstSpace);
						indexOfFirstSpace += 3;
						username = currentLine.substring(indexOfFirstSpace, indexOfLastSpace);
						password = currentLine.substring(indexOfLastSpace + 1);
						credentials.put(friendlyName, username + " " + password);
					}
				}
			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("Credentials database was not found."
					+ "\nPossible fix: restart the program.");
		}
		setExistingCredentials(credentialsMap);
	}

	public final void retrieveAndSetUsedIds() {
		Set<Integer> idSet = getExistingAccounts().keySet();
		ArrayList<Integer> existingIds = new ArrayList<>(idSet);

		setExistingUserIds(existingIds);
	}
	
	public final void modifyCredentialSet(String friendlyName, String username, String password) {
		File credentialsFile = new File(DOWNLOADS_PATH + "/credentials.txt");
		String entireFile = "";
		boolean foundCredentialSet = false;
		
		try {
			boolean foundUser = true;
			Scanner sc = new Scanner(credentialsFile);
			String currentLine;
			
			while (sc.hasNextLine()) {
				currentLine = sc.nextLine();
				
				if (currentLine.contains(Integer.toString(getSignedInUser())))
					foundUser = true;
				
				if (foundUser && currentLine.contains(friendlyName + " : ")) {
					foundCredentialSet = true;
					entireFile += "-" + friendlyName + " : " + EncryptAndDecrypt.encryptData(username) + " " + EncryptAndDecrypt.encryptData(password) + "\n";
					System.out.println("\n" + friendlyName + " was successfully modified.");
					continue;
				} else {
					entireFile += currentLine;
					entireFile += "\n";
				}	
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("An issue has occured when trying to modify this credential set.");
		}

		if (!foundCredentialSet) {
			System.out.println("\nCredential set \"" + friendlyName + "\" was not found.");
		} else {
			try {
				FileWriter writer = new FileWriter(DOWNLOADS_PATH + "/credentials.txt");
				writer.write(entireFile);
				writer.close();
			} catch (IOException e) {
				System.out.println("An issue has occured when trying to update the credentials database.");
			}
		}
	}

	public final void deleteCredentialSet(String friendlyName) {
		File credentialsFile = new File(DOWNLOADS_PATH + "/credentials.txt");
		String entireFile = "";
		boolean foundCredentialSet = false;
		
		try {
			boolean foundUser = true;
			Scanner sc = new Scanner(credentialsFile);
			String currentLine;
			
			while (sc.hasNextLine()) {
				currentLine = sc.nextLine();
				
				if (currentLine.contains(Integer.toString(getSignedInUser())))
					foundUser = true;
				
				if (foundUser && currentLine.contains(friendlyName + " : ")) {
					foundCredentialSet = true;
					System.out.println("\nCredential set \"" + friendlyName + "\" was deleted.");
					continue;
				} else {
					entireFile += currentLine;
					entireFile += "\n";
				}	
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("An issue has occured when trying to delete this credential set.");
		}

		if (!foundCredentialSet) {
			System.out.println("\nCredential set \"" + friendlyName + "\" was not found.");
		} else {
			try {
				FileWriter writer = new FileWriter(DOWNLOADS_PATH + "/credentials.txt");
				writer.write(entireFile);
				writer.close();
			} catch (IOException e) {
				System.out.println("An issue has occured when trying to update the credentials database.");
			}
		}
		readCredentialsFile();
	}
}

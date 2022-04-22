package passwordManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class DataManager {

	// Static dynamic start to path of downloads folder (then add on the specific
	// .txt)

	// Database for user accounts themselves (and passwords that are linked to it),
	// not the credentials

	/*
	 * Database for Account data: (separate from UsedIds for simplicity)
	 *
	 * Reader method:
	 * 
	 * Add these to the corresponding variables of an Account object upon sign in
	 * (not pw tho) For sign-in, test what the user wrote for the password against
	 * the password stored (will have already taken in the ID and checked for
	 * existence, so we can know the password)
	 * 
	 * @return Account so that it can be used as a variable inside of
	 * UserInputManager as to not make everything static
	 * 
	 * Writer method:
	 * 
	 * Uses toString() from Account
	 * 
	 * Format:
	 * { 
	 * toString() from Account 
	 * }
	 * 
	 */
	
	public static void readAccountData() { // return something? put it in Accounts or UserInputManager for reference?
		String path = "C:/Users/adam8/Downloads/userAccounts.txt";
		File accountDataFile = new File(path);
		Scanner sc;
		HashMap<Integer, Account> accountMap = new HashMap<Integer, Account>();
		try {
			sc = new Scanner(accountDataFile);
			String currentLine, name, password;
			int userId;
			
			while (sc.hasNextLine()) {
				currentLine = sc.nextLine();
				int indexOfFirstSpace = currentLine.indexOf(" "),
						indexOfLastSpace = currentLine.lastIndexOf(" ");
				if (currentLine.charAt(0) == '{' || currentLine.charAt(0) == '}') {
					continue;
				}
				else if (currentLine.length() > 1) {
					userId = Integer.parseInt(currentLine.substring(7, indexOfFirstSpace));
					name = currentLine.substring(indexOfFirstSpace + 6, indexOfLastSpace);
					password = currentLine.substring(indexOfLastSpace + 10);
					accountMap.put(userId, new Account(userId, name, password));
				}
				else {
					sc.close();
					break; // for potential trailing " " at end of file
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void writeAccountData(Account acc) {
		try {
			FileWriter writer = new FileWriter("C:/Users/adam8/Downloads/userAccounts.txt", true);
			writer.append("{\n" + acc.toString() + "\n}\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Creates and updates used user ID ArrayList Returns ArrayList<Integer> used in Account class
	 * @return ArrayList<Integer>
	 */
	public static ArrayList<Integer> createAndUpdateIdList() {
		String path = "C:/Users/adam8/Downloads/usedIds.txt";
		File userAccountsFile = new File(path);

		if (!userAccountsFile.exists()) {
			try {
				userAccountsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Scanner sc;
		try {
			sc = new Scanner(userAccountsFile);
			if (sc.hasNextLine()) {
				String usedIds = sc.nextLine();
				sc.close();

				ArrayList<Integer> usedIdsList = new ArrayList<>();
				String currentId = "";
	
				for (int i = 0; i < usedIds.length(); i++) {
					if (usedIds.charAt(i) != ',') {
						currentId += usedIds.charAt(i);
					} else {
						usedIdsList.add(Integer.parseInt(currentId));
						currentId = "";
					}
				}
				return usedIdsList;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	/*
	 * Adds used user ID to database
	 */
	public static void addUsedUserId(int userId) {
		try {
			FileWriter writer = new FileWriter("C:/Users/adam8/Downloads/usedIds.txt", true);
			writer.append(userId + ",");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Creates credentials database if it doesn't already exist
	 * Writes to credentials database based on parameters
	 */
	public static void addCredentialSetToDB(int userId, Credentials credentialSet) { // FINISH

		// For path, get java to search for downloads folder in the OS (dynamic)

		File credentialsFile = new File("C:/Users/adam8/Downloads/credentials.txt");

		if (!credentialsFile.exists()) {
			try {
				credentialsFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Plan:
		
		// Write in databse format (as discussed)
		// Be able to append data (provided in parameters) under a given userId (provided in parameters)
		// If that userId does not already have a spot on the DB, one must be made
		// Otherwise, append to the existing spot
	}

	public static HashMap<Integer, HashMap<String, String>> readCredentialsFile() {

		// For path, get java to search for downloads folder in the OS
		File path = new File("C:/Users/adam8/Downloads/credentials.txt"); // Change at the end
		HashMap<Integer, HashMap<String, String>> userIdHashMap = new HashMap<Integer, HashMap<String, String>>();
		Scanner sc;
		String currentLine, username, password, friendlyName;
		int userId = 0, indexOfFirstSpace, indexOfLastSpace;
		HashMap<String, String> credentials;

		try {
			sc = new Scanner(path);
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
						userIdHashMap.put(userId, credentials);
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
					// For case of '{', which was done for readability in the DB, do nothing
					// "nextUser" is put at the top of each section for both readability
					// and the fact that the check for sc.nextLine() = "" seems to make scanner
					// skip a line and miss the userId
					// "nextUser" acts as a placeholder
				}

			}
			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

//		System.out.println(userIdHashMap); // Remove later
		return userIdHashMap;
	}

}
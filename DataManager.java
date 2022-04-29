package passwordManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
		HashMap<Integer, Account> accountMap = new HashMap<>();
		String path = DOWNLOADS_PATH + "/userAccounts.txt";
		String currentLine, name, password;
		int userId;

		try {
			File accountDataFile = new File(path);
			
			if (!accountDataFile.exists()) {
				try {
					accountDataFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
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
					accountMap.put(userId, new Account(userId, name, password));
				} else {
					sc.close();
					break;
				}
			}
			// TODO:
			// sort accountMap by user Id (as per interface)
			setExistingAccounts(accountMap);
			retrieveAndSetUsedIds();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public final void writeAccountData(Account acc) {
		try {
			FileWriter writer = new FileWriter(DOWNLOADS_PATH + "/userAccounts.txt", true);
			writer.append("{\n" + acc.toString() + "\n}\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}

		// Eventually will switch to a Search algo (based on instructions)
		if (entireFile.contains("+" + Integer.toString(userId))) {
			for (int i = 0; i < entireFile.length(); i++) {
				// If ID is on DB
				if (entireFile.substring(i, i + 1).equals("+")
						&& entireFile.substring(i + 1, i + 6).equals(Integer.toString(userId))) {

					i += 7; // to skip over user id line

					entireFile = entireFile.substring(0, i + 2) + credentialSet.toString()
							+ entireFile.substring(i + 1);
					break;
				}
			}
		} else {
			// If not on DB: (adds userId entry)
			entireFile += "nextUser\n+" + Integer.toString(userId) + "\n{\n" + credentialSet.toString() + "\n}";
		}
		
		// Writes back to file
		try {
			FileWriter writer = new FileWriter(DOWNLOADS_PATH + "/credentials.txt");
			writer.write(entireFile);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
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
					e.printStackTrace();
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
			e.printStackTrace();
		}
		setExistingCredentials(credentialsMap);
	}

	public final void retrieveAndSetUsedIds() {
		Set<Integer> idSet = getExistingAccounts().keySet();
		ArrayList<Integer> existingIds = new ArrayList<>(idSet);

		setExistingUserIds(existingIds);
	}
	
	// TODO:
	
	public final HashMap<Integer, HashMap<String, String>> modifyCredentialSet() {

		return null;
	}

	public final HashMap<Integer, HashMap<String, String>> deleteCredentialSet() {

		return null;
	}
}
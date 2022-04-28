package passwordManager;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;

public class UserInputManager extends UserInteraction {

	private static Scanner sc;
	
	public UserInputManager() {

	}

	/*** PUT COMMENTS IN ABSTRACT CLASS UserInteraction **/
	
	public final void start(boolean greet) {
		
		readAccountData(); // Adds existing Account data from DB into HashMap
		
		readCredentialsFile(); // Adds existing Credentials data from DB into HashMap
		
		if (greet)
			System.out.println("Welcome to the password manager\n");
		
		System.out.println("Select from the following options:"
				+ "\n[1] Create account"
				+ "\n[2] Sign in");

		boolean exit = false;

		while (!exit) {
			System.out.print("\nChoice: ");
			int selection = handleIntInput();

			switch (selection) {
			case 1:
				createUserAccount();
				taskMenu();
				exit = true;
				break;
			case 2:
				signInProcedures();
				break;
			default:
				System.out.println("\nInvalid input, please try again!");
				break;
			}
		}
	}

	public final void createUserAccount() {
		sc = new Scanner(System.in);
		String name;
		String password;

		System.out.print("Enter your name: ");
		name = sc.nextLine();

		password = handlePasswordCreation();

		Account acc = new Account(EncryptAndDecrypt.encryptData(name), EncryptAndDecrypt.encryptData(password));
		writeAccountData(acc);
		readAccountData();
		setSignedInUser(acc.getUserId());
		
		System.out.println("Your user ID is " + acc.getUserId() + ". This ID will be required for sign in.");
	}

	/*
	 * The main menu after signing in / creating account
	 */
	public final void taskMenu() {

		boolean continueRequested = true;

		while (continueRequested) {
			boolean validSelectionMade = false;
			
			System.out.println("\nSelect next action:"
					+ "\n[1] Create credential set"
					+ "\n[2] Delete credential set"
					+ "\n[3] Modify credential set"
					+ "\n[4] View credential set"
					+ "\n[5] Sign out and exit");
			
			while (!validSelectionMade) {
				System.out.print("\nChoice: ");
				int choice = handleIntInput();

				switch (choice) { // from menu
				case 1:
					handleCredentialSetCreation();
					validSelectionMade = true;
					break;
				case 2:
					// has to do with hashmaps and the File IO
					validSelectionMade = true;
					break;
				case 3:
					// same thing
					validSelectionMade = true;
					break;
				case 4:
					// Put all in the function ***
					
					System.out.println("\nBelow are the credential sets which you have saved."
							+ "\nSelect the credential set you would like to access by typing the EXACT name:");
					
					System.out.println(super.getExistingCredentials().get(super.getSignedInUser())); // Format so it only shows
																									 // a list of friendly names
					System.out.print("\nSelection: ");
					
					if (sc.hasNextLine())
						sc.nextLine();
					
					String selection = sc.nextLine();
					displayCredentials(selection);
					
					validSelectionMade = true;
					break;
				case 5:
					validSelectionMade = true;
					continueRequested = false;
					break;
				default:
					System.out.println("Invalid option, please retry!");
					break;
				}
				
			}
				sc = new Scanner(System.in);
				
				if (!continueRequested)
					break;
				
				while (true) {
					System.out.print("\nWould you like to continue? [Y/N] ");
					String continueChoice = sc.nextLine();
					if (continueChoice.substring(0, 1).equalsIgnoreCase("y")) {
						break;
					} else if (continueChoice.substring(0, 1).equalsIgnoreCase("n")) {
						continueRequested = false;
						break;
					} else {
						System.out.println("Invalid input, please retry!");
					}
				}
			}
			System.out.print("\nSee you soon, "
					+ super.getExistingAccounts().get(getSignedInUser()).getName() + "!");
			
			System.exit(0); // Bad practice, fix eventually
		}

	//////////////////////////////////////////////////////////////////////////
	
	// TODO: follow comment left below
	
	public final Credentials handleCredentialSetCreation() {
		sc = new Scanner(System.in);
		String friendlyName, username, pw;
		
		System.out.print("\nSet name of this credential set: ");
		friendlyName = sc.nextLine();
		
		System.out.print("Username/email for this credential set: ");
		username = sc.nextLine();
		
		System.out.print("Password for this credential set: ");
		pw = sc.nextLine();
		
		Credentials credentialSet = new Credentials(friendlyName, username, pw); // Encrypt before placing
		super.addCredentialSetToDB(super.getSignedInUser(), credentialSet);
		
		System.out.println("\nCredential set added successfully!");
		return credentialSet;
	}
	
	// TODO:
	// Params may not be needed, nor friendlyname. Depends on implementation
	public final void displayCredentials(String friendlyName) {
		HashMap<String, String> credentialsMapOfUser = super.getExistingCredentials().get(super.getSignedInUser());
		
		String requestedCredentialSet= credentialsMapOfUser.get(friendlyName);
		
		System.out.println("\nDisplaying selected credential set under " + friendlyName + ":\n\n" + requestedCredentialSet);
		
		// List it nicely (username: --- \n password: ---)
		// Upon clicking enter, make it disappear and show menu for next action
		
	}

	// TODO:
	// Lists all friendly names of credentials on account
	// Must type the friendly name requested, which displays the credential set
	// When done, clearScreen()
	
	public final String listStoredUserData(int userId) {

		return "";
	}
	
	//////////////////////////////////////////////////////////////////////////

	public final int handleIntInput() {
		sc = new Scanner(System.in);

		try {
			int number = sc.nextInt();
			return number;
		} catch (InputMismatchException e) {
			return 0; // Brings to default of switch statement, which prints invalid and gets user to try again
		}
	}

	/*
	 * Passwords must be:
	 * 8 characters or longer
	 * Only contain characters with ASCII values 33-126
	 */
	public final String handlePasswordCreation() {
		sc = new Scanner(System.in);
		boolean meetsCriteria = false;

		while (!meetsCriteria) {
			System.out.print("Enter your password: ");
			String pw = sc.nextLine();
			String confirmedPw;
			boolean retry = false;

			if (pw.length() < 8) {
				System.out.println("\nPassword must be 8 characters or longer!");
				continue;
			}

			for (int i = 0; i < pw.length(); i++) {
				if ((int) (pw.charAt(i)) < 33 || (int) (pw.charAt(i)) > 126) {
					System.out.println("\nPassword can only be comprised of letters, numbers and symbols.");
					retry = true;
				}
			}
			if (retry)
				continue;

			while (true) {
				System.out.print("Confirm your password: ");
				confirmedPw = sc.nextLine();

				if (confirmedPw.equals(pw)) {
					System.out.println("Success! Password created.\n");
					super.clearScreen(); // Implement!!!
					meetsCriteria = true;
					break;
				} else {
					System.out.println("Passwords do not match. Try again.");
				}
			}
			return pw;
		}
		return null;
	}
	
	
	public final void signInProcedures() {
		sc = new Scanner(System.in);
		int userId, pwAttemptsLeft = 3;
		String password;
		HashMap<Integer, Account> accountMap = super.getExistingAccounts();
		
		System.out.print("Enter user ID: ");
		userId = handleIntInput();
		
		if (!accountMap.containsKey(userId)) {
			System.out.println("\nUser ID does not exist!\n");
			start(false);
		}
		else {
			
			while (pwAttemptsLeft > 0) {
				System.out.print("Enter password: ");
				
				if (sc.hasNextLine() && pwAttemptsLeft > 2) {
					sc.nextLine();
				}				
				password = sc.nextLine();
				
				if (!EncryptAndDecrypt.decryptData(accountMap.get(userId).getPassword()).equals(password)) {
					System.out.println("\nIncorrect password!\n");
					pwAttemptsLeft--;
					continue;
				} else {
					super.setSignedInUser(userId);
					System.out.println("\nWelcome, " + accountMap.get(userId).getName() + "!");
					break;
				}
			}
			
			if (pwAttemptsLeft == 0) {
				System.out.println("Going back to start screen!\n");
				start(false);
			} else {
				taskMenu();
			}
		}
		
	}

}
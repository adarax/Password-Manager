package passwordManager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInputManager extends UserInteraction {

	private static Scanner sc;

	public UserInputManager() {

	}

	public void start() {
		
		/*
		 * Call functions in DataManager to retrieve data on the database
		 * and add it to the corresponding variables (such as used user ids)
		 * 
		 * Make a separate function that gets called right away
		 */
		
		
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
				// sign in procedures (see UserInteraction abstract class)
				break;
			default:
				System.out.println("\nInvalid input, please try again!");
				break;
			}
		}
	}

	public void createUserAccount() {
		sc = new Scanner(System.in);
		String name;
		String password;

		System.out.print("Enter your name: ");
		name = sc.nextLine();

		password = handlePasswordCreation();

		Account acc = new Account(Encrypt.encryptData(name), Encrypt.encryptData(password));
		DataManager.writeAccountData(acc); // Writes to DB
		// Add to hashmap or something (need method for that)
		System.out.println("Your user ID is " + acc.getUserId() + ". This ID will be required for sign in.");
	}

	/*
	 * The main menu after signing in / creating account
	 */
	public void taskMenu() {

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
					// same thing
					validSelectionMade = true;
					break;
				case 5:
					System.out.print("\nSee you soon, " + Account.getName() + "!");
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
						System.out.print("\nSee you soon, " + Account.getName() + "!");
						break;
					} else {
						System.out.println("Invalid input, please retry!");
					}
				}
			}
		}

	/*
	 * Make use of the fact that their name was saved (ie. Here are "Account.getName()"'s) passwords:)
	 */
	public void displayCredentials(Account acc, String friendlyName) {

	}

	public String listStoredUserData(int userId) {

		return "";
	}

	public int handleIntInput() {
		sc = new Scanner(System.in);

		try {
			int number = sc.nextInt();
			return number;
		} catch (InputMismatchException e) {
			return 0; // Brings to default of switch statement, which prints invalid
					  // and gets user to try again
		}

	}

	/*
	 * Passwords must be:
	 * 8 characters or longer
	 * Only contain characters with ASCII values 33-126
	 */
	public String handlePasswordCreation() {
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
					clearScreen(); // Fix
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
	
	public Credentials handleCredentialSetCreation() {
		sc = new Scanner(System.in);
		String friendlyName, username, pw;
		
		System.out.print("Set name of this credential set: ");
		friendlyName = sc.nextLine();
		
		System.out.print("Username/email for this credential set: ");
		username = sc.nextLine();
		
		System.out.print("Password for this credential set: ");
		pw = sc.nextLine();
		
		Credentials credentialSet = new Credentials(friendlyName, username, pw);
		
		System.out.println("Credential set added successfully!");
		return credentialSet;
	}

}
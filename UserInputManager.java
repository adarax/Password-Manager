package passwordManager;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.HashMap;

public class UserInputManager extends UserInteraction {

	private static Scanner sc;
	
	public UserInputManager() {
		// Imports database into HashMaps
		super.readAccountData();
		super.readCredentialsFile();
	}
	
	public final void start(boolean greet) {
		if (greet)
			System.out.println("Welcome to the password manager\n");
		
		boolean validSelection = false;

		while (!validSelection) {
			System.out.println("Select from the following options:"
				+ "\n[1] Create account"
				+ "\n[2] Sign in"
				+ "\n[3] Help"
				+ "\n[4] Exit");
			System.out.print("\nChoice: ");
			
			int selection = handleIntInput();

			switch (selection) {
			case 1:
				createUserAccount();
				taskMenu();
				validSelection = true;
				break;
			case 2:
				signInProcedures();
				validSelection = true;
				break;
			case 3:
				programDescription();
				Scanner sc = new Scanner(System.in);
				sc.nextLine();
				// clearScreen();
				continue;
			case 4:
				System.out.print("\nProgram exited.");
				validSelection = true;
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
		
		System.out.println("Your user ID is " + acc.getUserId() + ". This ID will be required for sign-in.");
	}
	
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

				switch (choice) {
				case 1:
					handleCredentialSetCreation();
					validSelectionMade = true;
					break;
				case 2:
					String caseTwoSelection = listAndSelectCredentials("delete");
					if (!Objects.isNull(caseTwoSelection))
						super.deleteCredentialSet(caseTwoSelection);
					
					validSelectionMade = true;
					break;
				case 3:
					String caseThreeSelection = listAndSelectCredentials("modify");
					if (!Objects.isNull(caseThreeSelection)) {
						System.out.print("Set new username: ");
						String username = sc.nextLine();
						System.out.print("Set new password: ");
						String password = sc.nextLine();
						super.modifyCredentialSet(caseThreeSelection, username, password);
					}
					validSelectionMade = true;
					break;
				case 4:
					String caseFourSelection = listAndSelectCredentials("access");
					if (!Objects.isNull(caseFourSelection))
						displayCredentials(caseFourSelection);
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
					try {
						if (continueChoice.substring(0, 1).equalsIgnoreCase("y")) {
							break;
						} else if (continueChoice.substring(0, 1).equalsIgnoreCase("n")) {
							continueRequested = false;
							break;
						} else {
							System.out.println("\nInvalid input, please retry!");
						}
					} catch (StringIndexOutOfBoundsException e) {
						System.out.println("\nInvalid input, please retry!");
					}
				}
//				 super.clearScreen();
			}
			System.out.print("\nSee you soon, "
					+ super.getExistingAccounts().get(getSignedInUser()).getName() + "!");
		}
	
	public final void handleCredentialSetCreation() {
		sc = new Scanner(System.in);
		String friendlyName, username, pw;

		System.out.print("\nSet name of this credential set: ");
		friendlyName = sc.nextLine();

		System.out.print("Username/email for this credential set: ");
		username = sc.nextLine();

		System.out.print("Password for this credential set: ");
		pw = sc.nextLine();

		Credentials credentialSet = new Credentials(friendlyName, EncryptAndDecrypt.encryptData(username), EncryptAndDecrypt.encryptData(pw));
		HashMap<String, String> usersCredentials = super.getExistingCredentials().get(super.getSignedInUser());
		
		if (Objects.isNull(usersCredentials)) {
			super.addCredentialSetToDB(super.getSignedInUser(), credentialSet);
			System.out.println("\nCredential set added successfully!");
		}
		else if (!Objects.isNull(usersCredentials.get(friendlyName))) {
			System.out.println("\nError in creation: An entry under \"" + friendlyName
					+ "\" already exists!\nThere will be no way to differentiate between the two entires upon selection. Please retry!");
		} else {
			super.addCredentialSetToDB(super.getSignedInUser(), credentialSet);
			System.out.println("\nCredential set added successfully!");
		}
	}
	
	public final void displayCredentials(String friendlyName) {
		HashMap<String, String> credentialsMapOfUser = super.getExistingCredentials().get(super.getSignedInUser());
		
		try {
			String requestedCredentialSet = credentialsMapOfUser.get(friendlyName);
			int indexOfUsernameEnd = requestedCredentialSet.indexOf(" ");
			
			System.out.println("\nDisplaying selected credential set under " + friendlyName + ":");
			System.out.println("Username: " + EncryptAndDecrypt.decryptData(requestedCredentialSet.substring(0, indexOfUsernameEnd)));
			System.out.println("Password: " + EncryptAndDecrypt.decryptData(requestedCredentialSet.substring(indexOfUsernameEnd + 1)));
		}
		catch (Exception e) {
			System.out.println("\n" + friendlyName + " was not found!");
		}
	}
	
	public final String listAndSelectCredentials(String action) {
		super.readCredentialsFile();
		HashMap<String, String> userOwnedCredentialsSet = super.getExistingCredentials().get(super.getSignedInUser());
		
		if (Objects.isNull(userOwnedCredentialsSet)) {
			System.out.println("\nNo credential sets exist under this account!");
			return null;
		} else {
			Set<String> list = userOwnedCredentialsSet.keySet();
			String listAsString = "";
			
			for (String element : list)
				listAsString += "-> " + element + "\n";

			if (listAsString.length() < 1) {
				System.out.println("\nNo credential sets exist under this account!");
			} else {
				System.out.println("\nSelect the credential set you would like to " + action + " by typing the EXACT name:");
				System.out.println(listAsString);
				System.out.print("Selection: ");
				sc.nextLine(); // Consume rest of line
				String selection = sc.nextLine();
				return selection;
			}
			return null;
		}
	}

	public final int handleIntInput() {
		sc = new Scanner(System.in);

		try {
			int number = sc.nextInt();
			return number;
		} catch (InputMismatchException e) {
			return 0; // Brings to default of switch statement, which prints invalid and gets user to try again
		}
	}

	public final String handlePasswordCreation() {
		sc = new Scanner(System.in);
		boolean meetsCriteria = false;

		while (!meetsCriteria) {
			System.out.print("Create your password: ");
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
					System.out.println("\nSuccess! Account created.\n");
//					super.clearScreen(); // TODO
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
		} else {
			while (pwAttemptsLeft > 0) {
				System.out.print("Enter password: ");

				if (sc.hasNextLine() && pwAttemptsLeft > 2) { // To resolve line consumption issues
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
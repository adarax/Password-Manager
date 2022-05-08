package passwordManager;

import java.io.IOException;

public abstract class UserInteraction extends DataManager implements IUserInteraction {
    
	/*
     * Beings interaction with user
     * First thing to appear on screen
     * Includes greeting if the sign-in menu is being called for the first time
     * Asks user to set up account
     */
    public abstract void start(boolean greet);
    
    /*
     * Creates user account
     */
    public abstract void createUserAccount();
    
    /*
     * Displays task menu (main menu)
     * Uses Scanner to let user choose option
     * Calls appropriate method
     */
    public abstract void taskMenu();
    
	public final void clearScreen() {
		try {
			String currentOs = System.getProperty("os.name");
			if (currentOs.toLowerCase().contains("win")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\u001B[H\u001B[2J");
				System.out.flush();
			}
		} catch (InterruptedException | IOException e) {
			System.out.println("\nAn error occurred when clearing the screen.");
		}
	}
    
	/*
	 * Gives an explanation about what the program does and an example of how to use it
	 */
	public final void programDescription() {
		System.out.println(
			"\nThis program is a password manager."
				+ "\n\nA password manager stores credential sets, which consist of the name of the account/program for which the sign-in"
				+ "\ninformation is being saved, that account/program's corresponding username, and the corresponding password."
				+ "\n\nFor example, if a credential set was made for Gmail, the credential set name would be \"Gmail\", and the username"
				+ "\nand password would be the user's sign-in credentials."
				+ "\n\nPress the \'ENTER\' key to continue...");
	}
	
    /*
     * Displays all friendly names of credential sets owned by the user
     * Asks user to select the Credentials set they would like to view
     * @param action : lets the method have multiple purposes. Changes the prompt shown to the user
     * @return String : the friendlyName of the user's selection
     */
    public abstract String listAndSelectCredentials(String action);
    
    /*
     * Displays requested credential set based on name of set (obtained by user-input)
     */
    public abstract void displayCredentials(String friendlyName); 
    
    /*
     * Handles sign-in to the user's account
     */
    public abstract void signInProcedures();
    
    /*
     * Handles creation of credential set
     */
    public abstract void handleCredentialSetCreation();
    
    /*
     * Handles exceptions that may arise while inputting into int-only fields
     */
    public abstract int handleIntInput();
    
    /*
     * Guides user through creating a password for their account
	 * Passwords must be:
	 * 8 characters or longer
	 * Only contain characters with ASCII values 33-126
	 */
    public abstract String handlePasswordCreation();
}

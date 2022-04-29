package passwordManager;

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
    
    /*
     * See interface IUserInteraction for description
     */
	public final void clearScreen() {
		// TODO
		// Still need to find an implementation that does not leave an unknown character behind in the IDE
	}
    
    /*
     * Displays all friendly names of credential sets owned by the user
     * Asks user to select the Credentials set they would like to view
     */
    public abstract void listAndSelectCredentials();
    
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

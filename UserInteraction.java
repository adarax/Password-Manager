package passwordManager;

public abstract class UserInteraction {

    // May need to change access modifiers / static modifier
    
    /*
    * Beings interaction with user
    * First thing to appear on screen
    * Includes greeting, and asks user to set up account
    */
    public abstract void start();
    
    /*
     * Creates user account
     */
    public abstract void createUserAccount();
    
    /*
    * Displays task menu
    * Uses Scanner to let user choose option
    * Calls appropriate method
    */
    public abstract void taskMenu();
    
    // Clear command line screen (only works on CMD)
    public void clearScreen() {
    	
    	// Finish other things first, requires some research
    	
    }
    
    // friendlyName acts as key for hashmap
	// It's the name of the credential set requested
	// friendlyName should get passed from listStoredUserData()
    public abstract void displayCredentials(Account acc, String friendlyName); 

    // Gets hashmap of friendly names
    // Lists friendly names (display hashmap keys)
    // Returns friendlyName that user selects, to be displayed using displayCredentials
    
    // Eventually instead of using userId, may want to use the Account object
    // Will need a method that takes userId and return Account (after giving password)
    public abstract String listStoredUserData(int userId);
    
    
    // Set username
    // Set name
    // Set password
    public void signInProcedures() {
    	
    }
    
    /*
     * Handles creation of credential set
     */
    public abstract Credentials handleCredentialSetCreation();
    
    /*
     * Handles exceptions that may arise while inputting into int-only fields
     */
    public abstract int handleIntInput();
    
}

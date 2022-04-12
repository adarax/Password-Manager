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
    * Displays task menu
    * Uses Scanner to let user choose option
    * Calls appropriate method
    */
    public abstract void selectTask();

    /* (Implement this in abstact class)
    * Tests input and handles exceptions
    * If an exception is raised, let user try again
    * Verification of proper input will be based on type of task
    * Might need multiple functions or generic
    */
    public <T> T handleInput(T input, String type) { // May need to modify declaration

    	switch (type) {
    		case "password":
    		
    	}
    	
        // If type is pw, go based on password rules, return String cuz pw
        // If input is supposed to be a number, make sure it is and return int
        // Etc.

        // All input is using Scanner

 
        return null;
    }
    
    public void clearScreen() {
    	// Clear command line screen
    }
    
    public abstract void displayCredentials(Account acc, String friendlyName); // friendlyName acts as key for hashmap
    																		  // It's the name of the credential set requested
    																		  // friendlyName should get passed from listStoredUserData()

    // Gets hashmap of friendly names
    // Lists friendly names (display hashmap keys)
    // Returns friendlyName that user selects, to be displayed using displayCredentials
    
    // Eventually instead of using userId, may want to use the Account object
    // Will need a method that takes userId and return Account (after giving password) -> 
    public abstract String listStoredUserData(int userId);
    
    
    // Set username
    // Set name
    // Set password
    public void signInProcedures() {
    	
    }
    
    // Exit message
    // Called when user selects exit option
    // Terminates program
    public void signOut() {
    	
    }
    

}

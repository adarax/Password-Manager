public abstract class UserInteraction {

    // May need to change access modifiers / static modifier
    
    /*
    * Beings interaction with user
    * First thing to appear on screen
    * Includes greeting, and asks user to set up account
    */
    public void start();

    /*
    * Displays task menu
    * Uses Scanner to let user choose option
    * Calls appropriate method
    */
    public void selectTask();

    /* (Implement this in abstact class)
    * Tests input and handles exceptions
    * If an exception is raised, let user try again
    * Verification of proper input will be based on type of task
    * Might need multiple functions or generic
    */
    public <T> verifyInput(String input, String type) {

        // If type is pw, go based on password rules, return String cuz pw
        // If input is supposed to be a number, make sure it is and return int
        // Etc.

        // All input is using Scanner

 
        return null;
    }

    // Other methods needed    

}
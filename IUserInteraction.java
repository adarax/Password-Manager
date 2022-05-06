package passwordManager;

public interface IUserInteraction {
	
	/*
	 * Method clears screen of command line
	 * Checks which OS the program is being run on to use the right command
	 * Only works on command line, not in IDEs
	 */
	public void clearScreen();
}
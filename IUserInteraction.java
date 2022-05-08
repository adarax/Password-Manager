package passwordManager;

public interface IUserInteraction {
	
	/*
	 * Method clears screen of command line
	 * Checks which OS the program is being run on to use the right command
	 * Only works on the command line, leaves an "unknown" symbol in Eclipse IDE
	 */
	public void clearScreen();
}
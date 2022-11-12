package passwordManager;

public interface IAccount {

	/* Randomly generates 5-digit user ID
	 * Adds ID to an ArrayList and makes sure the ID is unique
	 * Returns ID
	 */
	int generateUserId();
	
	@Override
	String toString();
}

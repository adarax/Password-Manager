package passwordManager;

public interface IDataManager {
	/*
	 * Retrieves data from userAccounts.txt database
	 * Adds data to a HashMap<Integer, Account>
	 * Sorts HashMap<Integer, Account> by Account name (encrypted) in ascending order
	 * and if two names are equal it will sort them by user ID in increasing order
	 */
	public void readAccountData();
	
	/*
	 * Creates userAccounts.txt database if not already in existence
	 * Writes data of passed Account object into userAccounts.txt database
	 */
	public void writeAccountData(Account acc);
	
	/*
	 * Creates credentials.txt database if not already in existence
	 * Writes Credentials object to database, links Credentials to the user ID (owner)
	 */
	public void addCredentialSetToDB(int userId, Credentials credentialSet);
	
	/*
	 * Reads Credentials data from credentials.txt database
	 * Adds to HashMap<Integer, HashMap<String, String>>
	 * This links the user ID to a HashMap<String, String> of its corresponding credentials
	 */
	public void readCredentialsFile();
	
	/*
	 * Puts existing user IDs into an ArrayList<Integer>
	 * Easy access to used IDs for when Account creates new IDs
	 * Account checks this ArrayList to make sure it is not generating already-used IDs
	 */
	public void retrieveAndSetUsedIds();
	
	// Add interface for modifyCredentialsSet() and deleteCredentialsSet()
}
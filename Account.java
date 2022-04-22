package passwordManager;

import java.util.ArrayList;
import java.util.Random;

public class Account implements IAccount {

	private int userId;
	private static String name; // Static so that it can retrieved by UserInputManager in greetings
								// without creating an instance variable
								// May need to modify eventually, just make a static variable of the Account in use for this session
	private String password;
	private ArrayList<Integer> existingUserIds;
	
	
	// ******** Another variable: *********
	// The HashMap of passwords corresponding to the Account (will be instantiated
	// by a method in DataManager)
	public Account(String name, String password) {
		Account.name = name;
		this.password = password;
		this.existingUserIds = DataManager.createAndUpdateIdList();
		this.userId = generateUserId();
	}
	
	public Account(int userId, String name, String password) {
		this.userId = userId;
		Account.name = name;
		this.password = password;
		this.existingUserIds = DataManager.createAndUpdateIdList();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public static String getName() {
		return Decrypt.decryptData(Account.name);
	}

	public void setName(String name) {
		Account.name = Encrypt.encryptData(name);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Integer> getExistingUserIds() {
		return existingUserIds;
	}

	public void setExistingUserIds(ArrayList<Integer> existingUserIds) {
		this.existingUserIds = existingUserIds;
	}

	public int generateUserId() {
		Random r = new Random();
		int generatedId = r.nextInt(99999);

		if (!existingUserIds.contains(generatedId) && generatedId >= 10000) {
			DataManager.addUsedUserId(generatedId);
			return generatedId;
		} else {
			return generateUserId();
		}

	}

	@Override
	public String toString() { // This will be passed into the Accounts database
		return "UserId=" + this.userId + " Name=" + Account.name + " Password=" + this.password;
	}

}
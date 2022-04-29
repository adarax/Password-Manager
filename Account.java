package passwordManager;

import java.util.Random;

public class Account implements IAccount {

	private int userId;
	private String name;
	private String password;
	
	public Account(String name, String password) {
		this.name = name;
		this.password = password;
		this.userId = generateUserId();
	}
	
	public Account(int userId, String name, String password) {
		this.userId = userId;
		this.name = name;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return EncryptAndDecrypt.decryptData(name);
	}

	public void setName(String name) {
		this.name = EncryptAndDecrypt.encryptData(name);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public final int generateUserId() {
		Random r = new Random();
		int generatedId = r.nextInt(99999);
		
		if (!DataManager.getExistingUserIds().contains(generatedId) && generatedId >= 10000) {
			DataManager.getExistingUserIds().add(generatedId);
			return generatedId;
		} else {
			return generateUserId();
		}
	}

	@Override
	public String toString() {
		return "UserId=" + this.userId + " Name=" + this.name + " Password=" + this.password;
	}
}
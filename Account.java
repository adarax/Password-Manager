package passwordManager;

public class Account {
	
	int userId;
	String name;
	String password;
	
	// User id will be auto generated, and associated with a password
	// Name is just the name the user wants to be called
	// Password will be encrypted before being passed into the constructor
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() { // This will be passed into the Accounts database, and to get each use substring
		return "UserId=" + this.userId + " Name=" + this.name + " Password=" + this.password;
	}
	
}

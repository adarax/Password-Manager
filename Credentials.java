package passwordManager;

public class Credentials {
	
	String friendlyName;
	String username;
	String password;
	
	public Credentials(String friendlyName, String username, String password) {
		this.friendlyName = friendlyName;
		this.username = username;
		this.password = password; // Encrypted before put here
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "-" + this.friendlyName + " : " + this.username + " " + this.password;
	}
	
}

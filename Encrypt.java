package passwordManager;

public class Encrypt {
	
	/**
	 * Encrypting based on instructions found in "encryption and decryption plan - final project.txt".
	 */
	public static String encryptData(String plainText) {
		
		String encrypted = "";
		char currentChar;
		
		for (int i = 0; i < plainText.length(); i++) {
			
			currentChar = plainText.charAt(i);
			
			if (Character.isLetter(currentChar)) {
				encrypted += (char)((int)currentChar - 17);
				continue;
			}
			else if (Character.isDigit(currentChar)) {
				encrypted += (char)((int)(currentChar) + 24);
				continue;
			}
			else {
				encrypted += currentChar;
				continue;
			}

			
		}
		
		// Reverse encrypted
		
		String result = "";
		
		for (int i = 0; i < encrypted.length(); i++) {
			result = encrypted.charAt(i) + result;
		}
		
		// Add on "Ca$h" before and "Cow" after
		
		return "Ca$h" + result + "Cow";
	}
}

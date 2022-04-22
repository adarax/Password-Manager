package passwordManager;

public class Decrypt extends DataManager {

	// Instructions:

	// Remove Ha$h from beginning of string, Cow from end
	// Reverse
	// Decrypt from ROT-47
	// ASCII values are kept between 32 and 126
	
	public static String decryptData(String encryptedText) {
		
		String trimmed = encryptedText.substring(4, encryptedText.length() - 3);
		String reversed = "";
		
		for (int i = 0; i < trimmed.length(); i++) {
			reversed = trimmed.charAt(i) + reversed;
		}
		
		String decrypted = "";
		
		for (int i = 0; i < reversed.length(); i++) {
			char currentChar = reversed.charAt(i);

			int ascii = (int) currentChar;
			ascii -= 47;

			if (ascii < 32)
				ascii = (ascii + 126) - 32;
			
			decrypted += (char) ascii;
		}
		
		return decrypted;
	}
}

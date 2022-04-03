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
			
			// if symbol (part of defined symbol list) -> continue
			
			// Doing these two this way is faster then checking if it's part of a String
			if (currentChar == plainText.toUpperCase().charAt(i)) { // this would also get called for symbols
				encrypted += (char)((int)(currentChar) + 17);
				continue;
			}
			
			if (currentChar == plainText.toLowerCase().charAt(i)) {
				encrypted += (char)((int)(currentChar) - 7);
				continue;
			}
			//
			
			if (Character.isDigit(currentChar)) {
				encrypted += (char)((int)(currentChar) + 24);
				continue;
			}
			
			// leaves only symbols as possibility, which get left "as is"
			
		}
		
		// Reverse encrypted
		
		String reversed = "";
		
		for (int i = 0; i < encrypted.length(); i++) {
			reversed = encrypted.charAt(i) + reversed;
		}
		
		// Add on "Ca$h" before and "Cow" after
		
		return "Ca$h" + reversed + "Cow";
	}
}

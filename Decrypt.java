package passwordManager;

public class Decrypt {

	public static String decryptData(String encryptedText) {
		
		String encrypted = encryptedText.substring(4, (encryptedText.length() - 3));
		String decrypted = "";
		char currentChar;
		
		for (int i = 0; i < encrypted.length(); i++) {
			
			currentChar = encrypted.charAt(i);
			
			String symbols = "`~!@#$%^&*()-_=+[]{}\\|:;\"'<>,./?";

			// confuses letters from numbers... FIX THIS

			if (Character.isLetter((char)((int)currentChar + 17))) {
				decrypted += (char)((int)currentChar + 17);
				continue;
			}
			else if (Character.isDigit((char)(int)currentChar - 24)) {
				decrypted += (char)((int)currentChar - 24);
				continue;
			}
			else if (symbols.indexOf(currentChar) > -1) {
				
				if (Character.isLetter(currentChar)) {
					decrypted += (char)((int)currentChar + 17);
					continue;
				} else if (Character.isDigit(currentChar)) {
					decrypted += (char)((int)(currentChar) - 24);
					continue;
				} else {
					decrypted += currentChar;
				}
				
			}
			
		}
		
		// Reverse back to normal
		
		String result = "";
		
		for (int i = 0; i < decrypted.length(); i++) {
			result = decrypted.charAt(i) + result;
		}
		
		return result;
		
	}
}

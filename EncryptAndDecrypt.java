package passwordManager;

public class EncryptAndDecrypt {

	public static String encryptData(String plainText) {

		String encrypted = "";

		for (int i = 0; i < plainText.length(); i++) {
			char currentChar = plainText.charAt(i);

			int ascii = (int) currentChar;
			ascii += 47;

			if (ascii > 126)
				ascii = (ascii - 126) + 32;

			encrypted += (char) ascii;
		}
		String reversed = "";

		for (int i = 0; i < encrypted.length(); i++)
			reversed = encrypted.charAt(i) + reversed;

		return "Ha$h" + reversed + "Cow";
	}

	public static String decryptData(String encryptedText) {

		String trimmed = encryptedText.substring(4, encryptedText.length() - 3);
		String reversed = "";

		for (int i = 0; i < trimmed.length(); i++)
			reversed = trimmed.charAt(i) + reversed;

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
package passwordManager;

public class Encrypt {

	// Instructions:

	// Encrypting using ROT-47
	// Reverse
	// Add Ca$h before, Cow after
	// ASCII values are kept between 32 and 126

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

		for (int i = 0; i < encrypted.length(); i++) {
			reversed = encrypted.charAt(i) + reversed;
		}

		return "Ca$h" + reversed + "Cow";
	}

}
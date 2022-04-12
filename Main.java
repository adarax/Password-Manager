package passwordManager;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		// Testing encrypt / decrypt
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter something to encrypt: ");
		String enc = Encrypt.encryptData(sc.nextLine());
		sc.close();
		
		System.out.println("Encrypted: " + enc);
		System.out.println("Decrypted: " + Decrypt.decryptData(enc));
		//

		// Database.makeFile();
		// DataManager.readFile();

	}

}

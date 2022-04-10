package passwordManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;

public class Database {

    public static void makeFile() {

        File myFile = new File("C:/Users/adam8/Downloads/database.txt");

        try {
            myFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Reading from file

    public static HashMap<Integer, HashMap<String, String>> readFile() {

        File path = new File("C:/Users/adam8/Downloads/database.txt"); // Change at the end
        HashMap<Integer, HashMap<String, String>> userIdHashMap = new HashMap<Integer, HashMap<String, String>>();
        Scanner sc;
        String currentLine, username, password, friendlyName;
        int userId = 0, indexOfFirstSpace, indexOfLastSpace;
        HashMap<String, String> credentials;

        try {
            sc = new Scanner(path);
            boolean done = false;

            while (!done) {
                credentials = new HashMap<String, String>();
                
                if (!sc.hasNextLine() || sc.nextLine() == "") {
                    done = true;
                    continue;
                }

                while (sc.hasNextLine()) {
                    currentLine = sc.nextLine();
                    
                    if (currentLine.charAt(0) == '+') {
                        userId = Integer.parseInt(currentLine.substring(1));
                    }
                    else if (currentLine.charAt(0) == '}') {
                        userIdHashMap.put(userId, credentials);
                        break;
                    }
                    else if (currentLine.charAt(0) == '-') {
                        indexOfFirstSpace = currentLine.indexOf(" ");
                        indexOfLastSpace = currentLine.lastIndexOf(" ");
                        friendlyName = currentLine.substring(1, indexOfFirstSpace);
                        indexOfFirstSpace += 3;
                        username = currentLine.substring(indexOfFirstSpace, indexOfLastSpace);
                        password = currentLine.substring(indexOfLastSpace + 1);
                        credentials.put(friendlyName, username + " " + password);
                    }
                    // For case of '{', which was done for readability in the DB, do nothing
                    // "nextUser" is put at the top of each section for both readability
                    // and the fact that the check for sc.nextLine() = "" seems to make scanner
                    // skip a line and miss the userId
                    // "nextUser" acts as a placeholder
                }

            }
            sc.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        System.out.println(userIdHashMap); // Remove later
        return userIdHashMap;
    }

}
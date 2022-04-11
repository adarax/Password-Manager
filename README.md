# Password-Manager
A Java password manager which stores credentials into a .txt file database.

# Project outline and information
The scenario for this project is data storage, more specifically, password management. In today’s world,
we have many accounts that all require the creation of a username and password. The problem is that it
is hard for people to memorize all these usernames and passwords. One of the solutions to this is
creating a password which you reuse all over the internet. However, this poses a massive security risk
should this password ever get discovered by a hacker. It’s recommended to have a different password
for every website, but this is easier said than done. Remembering dozens of passwords is nearly
impossible, and that is the issue that this project will solve. I want to create a password manager, which
stores and encrypts passwords. The passwords are paired with the username/email as well as the
website or application it pertains to. This way, someone can keep all their passwords secure within a
single account, and they only have to remember one username-password combination.
The program will flow in the following way: the user will be asked to create an account for the password
manager which requires a username and password. Each account is an object of the Account class and
will have a randomly generated ID, which will be used to make calls to the account’s corresponding table
on the database. Once the account is created, the credentials will get encrypted via the Encrypt class
and sent to a database via the DataManager class, which is the parent of both Encrypt and Decrypt.
When the user wants to add the credentials for a website/program, the credentials become an object of
the Credentials class and go through a procedure similar to that of the account data (i.e., encrypted and
then sent to the database). When the user wants to retrieve their data, the DataManager class calls it
from the database, and it is then decrypted and displayed using the Decrypt class. The encryption
algorithm will be original. All inputs from the user as well as any exceptions that may be raised will be
handled by the UserInteraction abstract class as well as the implementing subclass UserInputManager. A
list of the friendly names set by the user will be stored in a .txt file using File I/O procedures. Usernames,
passwords, and the friendly name will be saved as key/value pairs on the database, in the form
{friendlyName = username – password}. Finally, as far as creating interfaces, I plan to have the following:
IEncrypt, IDecrypt, and IAccount.
Overall, this project lets a user store all their usernames and passwords in a safe place. The application is
accessed via the terminal. The “sign in” prompt will be the first thing the user will see, and there will also
be an option to “create a new account”. Navigation will be done using number values associated with an
action. Upon signing out, the session will end. However, as explained above, the data stored will not get
deleted.
The key features will be “add credential set”, where the user must enter the friendly name,
username/email, and password for the given credential set they want to store. The second feature will
be “remove credential set”, where upon selecting the set that they want to delete and confirming, the
set will be removed from the user’s account and database, permanently. The third feature will be
“modify credential set”, where the user can modify the details of an existing username/email, password,
or friendly name. Finally, there will be a “view credential set” feature where the user will select the
credential set, and the username/email and password of this set will be displayed until the user presses
a key. Upon key press, the screen will be cleared. After each action, the user will be prompted with a
menu to select the next action or to sign out. Signing out will terminate the program

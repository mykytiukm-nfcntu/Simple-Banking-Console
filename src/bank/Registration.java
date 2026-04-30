package bank;

//DoS

public class Registration extends AccountDBManager {
	public boolean createNewUser(String username,
							 String password) {
		if (password.length() < 4) {
			System.out.println("Password should be at lest 4 chars long");
			return false;
		}
		if (userExists(username)) {
			System.out.println("Such username already exists");
			return false;
		}
		Account newUser = new Account(username);
		storeNewUser(newUser, password);
		newUser.initDBUser();
		return true;
	}
}

package bank;

public class Authenticator extends AccountDBManager {
	public boolean auth(String username, String password) {
		String storedPassword = readPassword(username);
		System.out.println(password + " " + storedPassword);
		if (storedPassword.isEmpty()) {
			return false;
		}
		if (storedPassword.equals(password))
			return true;
		return false;
	}
}

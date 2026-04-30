package bank;


public class AuthWorker extends InputWorker {
	Registration registr;
	Authenticator auth;

	AuthWorker() {
		this.registr = new Registration();
		this.auth = new Authenticator();
	}

	void registration() {
		while (true) {
			System.out.println("Registration!! Enter ~ to exit\n\n");
			System.out.print("Enter username: ");

			String name = Programm.scanner.getNextLine();
			if (name.equals("~")) {
				break;
			}

			System.out.print("Enter password: ");
			String password = Programm.scanner.getNextLine();

			boolean OK = registr.createNewUser(name, password);
			if (OK) {
				System.out.println("Registration succeeded");
				break;
			}
			System.out.println("Try again\n\n");
		}
	}

	boolean authenticate() {
		boolean authOK = false;
		
		while (true) {
			System.out.println("Authentication!! Enter ~ to exit\n\n");
			System.out.print("Enter username: ");

			String name = Programm.scanner.getNextLine();
			if (name.equals("~")) {
				break;
			}
			
			System.out.print("Enter password: ");
			String password = Programm.scanner.getNextLine();
			boolean OK = auth.auth(name, password);
			if (OK) {
				System.out.println("Wellcome, " + name + "!!");
				authOK = true;
				Session.authenticatedUserName = name;
				break;
			}
			System.out.println("Invalid username or password");
		}
		return authOK;
	}

	@Override
	public boolean userInputLoop() {
		boolean authOK = false;
		while (true) {
			System.out.println("1 - Enter id and password");
			System.out.println("2 - Registration");
			System.out.println("3 - Exit");

			try {
				int action = Integer.parseInt(Programm.scanner.getNextLine());
				if (action == 3)
					break;
				switch (action) {
				case 1:
					authOK = authenticate();
					break;
				case 2:
					registration();
					break;
				default:
					System.out.println("Invalid enter, should be less then 4");
					break;
				}
				if (authOK)
					break;
			} catch (Exception ex) {
				System.out.println("Please enter integers");
			}
		}
		return authOK;
	}
}

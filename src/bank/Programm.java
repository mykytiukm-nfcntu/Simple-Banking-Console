package bank;

public class Programm {
	static utils.KeyboardScanner scanner = utils.KeyboardScanner.getInstance();
	static boolean isAuth = true;
	
	public static void main(String[] params) {

		InputWorker worker;
		AuthWorker authW = new AuthWorker();
		AccountWorker accountW = new AccountWorker();
		while (true) {
			if (isAuth)
				worker = authW;
			else
				worker = accountW;
			boolean shouldSwitch = worker.userInputLoop();
			if (shouldSwitch) {
				worker = isAuth ? accountW : authW;
				isAuth = !isAuth;
			} else {
				break;
			}
		}
		scanner.close();
	}
}

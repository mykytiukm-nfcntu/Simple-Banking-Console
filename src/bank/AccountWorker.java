package bank;

public class AccountWorker extends InputWorker {
	Account account;
	
	@Override
	public boolean userInputLoop() {
		account = new Account(Session.authenticatedUserName);
		Bank bank = Bank.getInstance();
		
		outerLoop:
		while (true) {
			System.out.println("Hello, " + account.name + "! What would you like today?\n\n");
			System.out.println("1 - Show balance"); // OK
			System.out.println("2 - Send money");
			System.out.println("3 - Credit");
			System.out.println("4 - Deposit");
			System.out.println("5 - Logout"); // OK
			System.out.println("6 - Exit"); // OK
			
			try {
				int action = Integer.parseInt(Programm.scanner.getNextLine());
				switch(action) {
				case 1:
					account.ShowBalance();
					break;
				case 2:
					// "Alex"
					account.makeMoneyFlow(account, new Account("Alex"), 1000);
					break;
				case 3:
					// 1000;
					account.makeMoneyFlow(bank, account, 1000);
					break;
				case 4:
					// 1000;
					account.makeMoneyFlow(account, bank, 1000);
					break;
				case 5:
					Session.authenticatedUserName = null;
					return true;
				case 6:
					break outerLoop;
				default:
					System.out.println("Please enter integers less then 7");
					continue;
				}
			} catch(Exception ex) {
				System.out.println("Please enter integers" + ex);
			}
		}
		return false;
	}
}


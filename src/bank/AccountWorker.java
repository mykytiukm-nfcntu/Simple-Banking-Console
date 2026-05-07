package bank;

enum UserAction {
	ShowBalance(1), SendMoney(2), ShowCredits(3), MakeCredit(4), Deposit(5), Logout(6), Exit(7);

	private final int value;

	UserAction(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static UserAction fromInt(int i) {
		for (UserAction s : UserAction.values()) {
			if (s.value == i)
				return s;
		}
		throw new IllegalArgumentException("Unknown code: " + i);
	}
}

class CreditWorker {
	Bank bank;

	CreditWorker(Bank bank) {
		this.bank = bank;
	}
	
	public void makeCredit(Account user) {
		int creditAmount = 0;
		// 1000;
		// account.makeMoneyFlow(bank, account, 1000);

		// displayFeeForCredit(amount);
		// Yes -> makeCredit();
		// No -> continue;
		while (true) {
			try {
				System.out.print("How much money do you need? : ");
				creditAmount = Integer.parseInt(Programm.scanner.getNextLine());
				if (creditAmount < 1000) {
					System.out.println("Credit is too smal, should be at least 1000");
					continue;
				}
				break;
			} catch (Exception ex) {
				System.out.println("Please enter integers");
			}
		}
		final int fee = bank.displayCreditFee(user, creditAmount);
		System.out.println("Calculation of the credit:");
		System.out.println("\tAmount: " + creditAmount);
		System.out.println("\tfee for 1 month: " + fee);
		System.out.println("\tOverall summ after 1 month: " + (fee + creditAmount));

		boolean agreed = false;
		while (true) {
			System.out.print("Do you agree? y/n: ");
			String userInput = Programm.scanner.getNextLine();
			System.out.println("UserInput: " + userInput);
			if (userInput.isBlank()) {
				continue;
			}
			userInput = userInput.trim();
			if (userInput.equals("y")) {
				agreed = true;
			} else if (userInput.equals("n")) {
				agreed = false;
			} else {
				continue;
			}

			break;
		}
		if (agreed) {
			System.out.println("You got " + creditAmount + " of credit. Please be aware that the fee will be increased after 12 month of keeping it");
			bank.makeCredit(user, creditAmount);
		}
	}
}

class UserActionPicker {
	UserAction pickUserAction() {
		UserAction action;
		while (true) {
			for (UserAction _action : UserAction.values()) {
				System.out.println(_action.name() + " - " + _action.getValue());
			}

			try {
				int actionI = Integer.parseInt(Programm.scanner.getNextLine());
				action = UserAction.fromInt(actionI);
				return action;
			} catch (Exception ex) {
				System.out.println("Please enter integers" + ex);
			}
		}
	}
}

public class AccountWorker extends InputWorker {
	Account account;
	Bank bank;
	UserActionPicker actionPicker;
	CreditWorker creditWorker;

	private void displayFeeForCredit(int amount) {
		int fee = bank.displayCreditFee(account, amount);
		System.out.println("Fee for this credit will be " + fee + ", increasing each month");
	}

	AccountWorker() {
		this.bank = Bank.getInstance();
		this.actionPicker = new UserActionPicker();
		this.creditWorker = new CreditWorker(bank);
	}
	
	@Override
	public boolean userInputLoop() {
		account = new Account(Session.authenticatedUserName);
		

		System.out.println("Hello, " + account.name + "! What would you like today?\n\n");
		outerLoop: while (true) {
			UserAction action = actionPicker.pickUserAction();
			switch (action) {
			case ShowBalance:
				account.ShowBalance();
				break;
			case SendMoney:
				/*String name = getNameFromUser();
				Account receiver = globalAccounts.find(name);
				account.makeMoneyFlow(account, new Account("Alex"), 1000);
				*/
				break;
			case MakeCredit:
				creditWorker.makeCredit(account);
				break;
			case ShowCredits:
				account.displayCurrentCredits();
				break;
			case Deposit:
				// 1000;
				//account.makeMoneyFlow(account, bank, 1000);
				break;
			case Logout:
				Session.authenticatedUserName = null;
				return true;
			case Exit:
				break outerLoop;
			default:
				System.out.println("Please enter integers less then 7");
				continue;
			}
			System.out.println();
		}
		return false;
	}
}

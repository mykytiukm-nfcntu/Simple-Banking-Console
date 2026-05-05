package bank;

enum UserAction {
	ShowBalance(1),
	SendMoney(2),
	ShowCredits(3),
	MakeCredit(4),
	Deposit(5),
	Logout(6),
	Exit(7);
	
	private final int value;

    UserAction(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    public static UserAction fromInt(int i) {
        for (UserAction s : UserAction.values()) {
            if (s.value == i) return s;
        }
        throw new IllegalArgumentException("Unknown code: " + i);
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
			} catch(Exception ex) {
				System.out.println("Please enter integers" + ex);
			}
		}
	}
}

public class AccountWorker extends InputWorker {
	Account account;
	Bank bank;
	UserActionPicker actionPicker;
	private void displayFeeForCredit(int amount) {
		int fee = bank.displayFee(account, amount);
		System.out.println("Fee for this credit will be " + fee + ", increasing each month");
	}
	
	@Override
	public boolean userInputLoop() {
		account = new Account(Session.authenticatedUserName);
		bank = Bank.getInstance();
		actionPicker = new UserActionPicker();
		
		System.out.println("Hello, " + account.name + "! What would you like today?\n\n");
		outerLoop:
		while (true) {
				UserAction action = actionPicker.pickUserAction();
				switch(action) {
				case ShowBalance:
					account.ShowBalance();
					break;
				case SendMoney:
					// "Alex"
					account.makeMoneyFlow(account, new Account("Alex"), 1000);
					break;
				case MakeCredit:
					// 1000;
					//account.makeMoneyFlow(bank, account, 1000);
					
					//displayFeeForCredit(amount);
					//Yes -> makeCredit();
					//No -> continue;
					bank.makeCredit(account, 1000);
					break;
				case ShowCredits:
					account.displayCurrentCredits();
					break;
				case Deposit:
					// 1000;
					account.makeMoneyFlow(account, bank, 1000);
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


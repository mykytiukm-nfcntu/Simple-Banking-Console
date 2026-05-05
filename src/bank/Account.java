package bank;

public class Account extends TransactionParticipant {
	CreditsManager credits;
	public Account(String name) {
		super(name);
		this.credits = new CreditsManager(this);
	}
	
	void ShowBalance() {
		System.out.println(String.format("%s user has %d balance", this.name, this.balance));
	}
	
	void initDBUser() {
		initUserInDB();
	}
	
	void displayCurrentCredits() {
		for (Credit credit : credits.getCredits()) {
			System.out.println("");
		}
	}
}

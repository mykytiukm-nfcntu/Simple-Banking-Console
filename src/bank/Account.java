package bank;

public class Account extends TransactionParticipant {
	public Account(String name) {
		super(name);
	}
	
	void ShowBalance() {
		System.out.println(String.format("%s user has %d balance", this.name, this.balance));
	}
	
	void initDBUser() {
		initUserInDB();
	}
}

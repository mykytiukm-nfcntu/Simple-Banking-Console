package bank;

public class Bank extends TransactionParticipant {
	static final private String BANKUSERNAME = "Bank";

	private static Bank instance;

	private Bank() {
		super(BANKUSERNAME);
	}

	public static Bank getInstance() {
		if (instance == null) {
			instance = new Bank();
		}
		return instance;
	}
}

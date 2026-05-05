package bank;

import java.time.LocalDate;

public class Bank extends TransactionParticipant {
	static final private String BANKUSERNAME = "Bank";
	private static Bank instance;
	
	private CreditsDBManager creditsManager;
	
	private Bank() {
		super(BANKUSERNAME);
		creditsManager = new CreditsDBManager();
	}

	public static Bank getInstance() {
		if (instance == null) {
			instance = new Bank();
		}
		return instance;
	}
	
	int displayFee(Account account, int amount) {
		// 
		return 50; // TODO!;
	}
	
	Credit makeCredit(Account account, int amount) {
		Credit credit = new Credit(account.name,
					amount,
					displayFee(account, amount),
					LocalDate.now().plusDays(30)
					);
		
		creditsManager.storeNewCredit(credit);
		
		return credit;
	}
	
}
